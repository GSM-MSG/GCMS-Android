package com.msg.gcms.ui.component.clubmaker.searchstudent

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.ActivityUserSearchBinding
import com.msg.gcms.ui.adapter.UserSearchAdapter
import com.msg.gcms.ui.base.BaseActivity
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
    }

    fun onClickListener(view: View) {
        when (view.id) {
            binding.goBackBtn.id -> {
                finish()
            }
            binding.selectBtn.id -> {
                Log.d("TAG", "$memberList")
            }
        }
    }

    private fun settingRecyclerView() {
        searchAdapter = UserSearchAdapter()
        with(binding.studentListRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(16))
            adapter = searchAdapter
        }
        searchAdapter.setItemOnClickListener(object : UserSearchAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                if(memberList.contains(userList[position])) {
                    memberList.remove(userList[position])
                    Log.d("TAG", "removeMemberList : $memberList")
                } else {
                    memberList.add(userList[position])
                    Log.d("TAG", "addMemberList : $memberList")
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