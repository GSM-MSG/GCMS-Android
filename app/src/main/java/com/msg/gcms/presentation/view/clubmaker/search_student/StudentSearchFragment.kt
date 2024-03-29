package com.msg.gcms.presentation.view.clubmaker.search_student

import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentStudentSearchBinding
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData
import com.msg.gcms.presentation.adapter.add_member.AddMemberAdapter
import com.msg.gcms.presentation.adapter.add_member.AddMemberType
import com.msg.gcms.presentation.adapter.user_search.UserSearchAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.utils.ItemDecorator
import com.msg.gcms.presentation.utils.keyboardHide
import com.msg.gcms.presentation.viewmodel.MakeClubViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class StudentSearchFragment :
    BaseFragment<FragmentStudentSearchBinding>(R.layout.fragment_student_search) {

    private val makeClubViewModel by activityViewModels<MakeClubViewModel>()

    private lateinit var searchAdapter: UserSearchAdapter
    private lateinit var addMemberAdapter: AddMemberAdapter

    private var userList = mutableListOf<GetSearchUserData>()
    private var addedMemberList = mutableListOf<AddMemberType>()

    private val coroutineJob: Job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    private val searchQuery = MutableStateFlow("")

    override fun initView() {
        binding.fragment = this
        settingRecyclerView()
        observeEditText()
        binding.studentSearchLayout.setOnClickListener {
            keyboardHide(requireActivity(), binding.searchEt)
        }
    }

    override fun observeEvent() {
        observeSearchResult()
    }

    private fun settingRecyclerView() {
        searchAdapter = UserSearchAdapter()
        with(binding.studentListRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(32, "VERTICAL"))
            adapter = searchAdapter
        }

        addMemberAdapter = AddMemberAdapter()
        with(binding.memberListRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(16, "HORIZONTAL"))
            adapter = addMemberAdapter
        }
        addMemberAdapter.setItemOnClickListener(object : AddMemberAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                addedMemberList.removeAt(position)
                addMemberAdapter.submitList(addedMemberList)
                binding.memberListRv.adapter = addMemberAdapter
            }
        })
        searchAdapter.setItemOnClickListener(object : UserSearchAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val item = userList[position]
                if (addedMemberList.map { it.uuid }.contains(item.uuid)) {
                    Log.d("TAG", "that user contained list")
                } else {
                    addedMemberList.add(
                        AddMemberType(
                            uuid = item.uuid,
                            userName = item.name,
                            userImg = item.profileImg
                        )
                    )
                    addMemberAdapter.submitList(addedMemberList)
                    binding.memberListRv.adapter = addMemberAdapter
                    Log.d("TAG", "addMemberList : $addedMemberList")
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
                    makeClubViewModel.getSearchUser(it)
                }
        }
    }

    private fun observeSearchResult() {
        makeClubViewModel.searchUserResult.observe(this) {
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
                if (addedMemberList.isNotEmpty()) {
                    makeClubViewModel.addedMemberList.clear()
                    makeClubViewModel.addedMemberList.addAll(addedMemberList)
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