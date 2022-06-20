package com.msg.gcms.ui.component.clubmaker.searchstudent

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.AddMemberType
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.ActivityUserSearchBinding
import com.msg.gcms.ui.adapter.AddMemberAdapter
import com.msg.gcms.ui.adapter.UserSearchAdapter
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.clubmaker.MakeClubActivity
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.UserViewModel
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
class UserSearchActivity : BaseActivity<ActivityUserSearchBinding>(R.layout.activity_user_search) {

    private val userViewModel by viewModels<UserViewModel>()

    private lateinit var searchAdapter: UserSearchAdapter
    private lateinit var addMemberAdapter: AddMemberAdapter

    private var userList = mutableListOf<UserData>()
    private val memberList = mutableListOf<UserData>()

    private val coroutineJob: Job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    private val searchQuery = MutableStateFlow("")

    override fun viewSetting() {
        binding.activity = this
        settingRecyclerView()
    }

    override fun observeEvent() {
        observeEditText()
        observeResult()
        getClubType()
    }

    private fun getClubType() {
        Log.d("TAG", "getClubType: ${intent.getStringExtra("clubType")}")
        userViewModel.clubType = intent.getStringExtra("clubType").toString()
    }

    fun onClickListener(view: View) {
        when (view.id) {
            binding.goBackBtn.id -> {
                finish()
            }
            binding.selectBtn.id -> {
                Log.d("TAG", "$memberList")
                val intent = Intent(this, MakeClubActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                Log.d("TAG", "memberList : ${memberList.toList()}")
                intent.putExtra("list", memberList.toString())
                startActivity(intent)
                finish()
            }
        }
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
                    userViewModel.getSearchUser(it)
                }
        }
    }

    private fun observeResult() {
        userViewModel.result.observe(this) {
            userList = it as MutableList<UserData>
            searchAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}