package com.msg.gcms.ui.component.clubmaker.searchstudent

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
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
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class UserSearchActivity : BaseActivity<ActivityUserSearchBinding>(R.layout.activity_user_search) {

    private val userViewModel by viewModels<UserViewModel>()

    private lateinit var searchAdapter: UserSearchAdapter

    private val userList = mutableListOf<UserData>()
    private val memberList = mutableListOf<UserData>()

    private val coroutineJob: Job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    override fun viewSetting() {
        binding.activity = this
        settingRecyclerView()
    }

    override fun observeEvent() {
        observeEditText()
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
            searchAdapter.submitList(userList)
            adapter = searchAdapter
        }
        searchAdapter.setItemOnClickListener(object : UserSearchAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                memberList.add(userList[position])
            }
        })
    }

    private fun observeEditText() {
        binding.searchEt.doAfterTextChanged {
            Log.d("TAG", "observeEditText: $it")
            userViewModel.searchQuery.value = it.toString()
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}