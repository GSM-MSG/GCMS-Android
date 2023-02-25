package com.msg.gcms.presentation.view.editclub

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEditSearchBinding
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData
import com.msg.gcms.presentation.adapter.add_member.AddMemberAdapter
import com.msg.gcms.presentation.adapter.add_member.AddMemberType
import com.msg.gcms.presentation.adapter.user_search.UserSearchAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.utils.ItemDecorator
import com.msg.gcms.presentation.viewmodel.EditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class EditSearchFragment : BaseFragment<FragmentEditSearchBinding>(R.layout.fragment_edit_search) {

    private val editViewModel by activityViewModels<EditViewModel>()

    private lateinit var searchAdapter: UserSearchAdapter
    private lateinit var addMemberAdapter: AddMemberAdapter

    private var userList = mutableListOf<GetSearchUserData>()
    private var memberList = mutableListOf<AddMemberType>()

    private val coroutineJob: Job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    private val searchQuery = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchAdapter = UserSearchAdapter()
        addMemberAdapter = AddMemberAdapter()
    }

    override fun init() {
        binding.fragment = this
        observeEvent()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        settingRecyclerView()
        memberList = editViewModel.addedMemberData.value!!.toMutableList()
    }

    private fun observeEvent() {
        observeEditText()
        observeResult()
    }

    private fun settingRecyclerView() {
        with(binding.studentListRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(32, "VERTICAL"))
            adapter = searchAdapter
        }
        with(binding.memberListRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(16, "HORIZONTAL"))
            // TODO 타입 변경하기
            addMemberAdapter.submitList(
                editViewModel.addedMemberData.value!!.map {
                    AddMemberType(
                        uuid = it.uuid,
                        userName = it.userName,
                        userImg = it.userImg
                    )
                })
            adapter = addMemberAdapter
        }
        addMemberAdapter.setItemOnClickListener(object : AddMemberAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val item = memberList[position]
                memberList.remove(item)
                addMemberAdapter.submitList(memberList)
                binding.memberListRv.adapter = addMemberAdapter
            }
        })
        searchAdapter.setItemOnClickListener(object : UserSearchAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val item = userList[position]
                if (!memberList.map { it.uuid }.contains(item.uuid)) {
                    memberList.add(
                        AddMemberType(
                            uuid = item.uuid,
                            userName = item.name,
                            userImg = item.profileImg
                        )
                    )
                    addMemberAdapter.submitList(memberList)
                    binding.memberListRv.adapter = addMemberAdapter
                    Log.d("TAG", "addMemberList : $memberList")
                } else {
                    Log.d("TAG", "that user contained list")
                }
            }
        })
    }

    private fun observeEditText() {
        binding.searchEt.doAfterTextChanged {
            searchQuery.value = it.toString()
            Log.d("TAG", "observeEditText: $it")
        }

        lifecycleScope.launch {
            searchQuery
                .filter { it.isNotEmpty() }
                .debounce(300L)
                .distinctUntilChanged()
                .collect {
                    Log.d("TAG", "observeEditText: $it")
                    editViewModel.getSearchUser(it, editViewModel.clubInfo.value!!.type)
                }
        }
    }

    private fun observeResult() {
        editViewModel.result.observe(this) {
            userList = it as MutableList<GetSearchUserData>
            searchAdapter.submitList(it)
        }
    }

    fun onClickListener(view: View) {
        when (view.id) {
            binding.goBackBtn.id -> {
                this.findNavController().popBackStack()
            }
            binding.selectBtn.id -> {
                if (memberList.isNotEmpty()) {
                    // TODO 여기 타입 변경하기
                    Log.d("TAG", "onClickListener: $memberList")
                    editViewModel.changeMemList(memberList)
                } else {
                    Log.d("TAG", "onClickListener: $memberList")
                }
                this.findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}