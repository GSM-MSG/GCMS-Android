package com.msg.gcms.ui.component.clubmaker.searchstudent

import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.AddMemberType
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.FragmentStudentSearchBinding
import com.msg.gcms.ui.adapter.AddMemberAdapter
import com.msg.gcms.ui.adapter.UserSearchAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.MakeClubViewModel
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

    private var userList = mutableListOf<UserData>()
    private var memberList = mutableListOf<UserData>()

    private val coroutineJob: Job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    private val searchQuery = MutableStateFlow("")

    override fun init() {
        binding.fragment = this
        observeEvent()
        settingRecyclerView()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    private fun observeEvent() {
        observeEditText()
        observeResult()
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
                val item = memberList[position]
                memberList.remove(memberList[position])
                addMemberAdapter.removeMember(AddMemberType(item.name, item.email, item.userImg))
            }
        })
        searchAdapter.setItemOnClickListener(object : UserSearchAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val item = userList[position]
                if(!memberList.contains(item)) {
                    memberList.add(item)
                    addMemberAdapter.submitList(AddMemberType(item.name, item.email, item.userImg))
                    Log.d("TAG", "addMemberList : $memberList")
                }else {
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
                    makeClubViewModel.getSearchUser(it)
                }
        }
    }

    private fun observeResult() {
        makeClubViewModel.result.observe(this) {
            userList = it as MutableList<UserData>
            searchAdapter.submitList(it)
        }
    }

    fun onClickListener(view: View) {
        when (view.id) {
            binding.goBackBtn.id -> {
                this.findNavController().popBackStack()
            }
            binding.selectBtn.id -> {
                makeClubViewModel.memberList = memberList
                makeClubViewModel.setMemberEmail()
                this.findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }

}