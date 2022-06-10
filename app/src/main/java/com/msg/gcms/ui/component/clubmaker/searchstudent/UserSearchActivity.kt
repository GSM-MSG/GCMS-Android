package com.msg.gcms.ui.component.clubmaker.searchstudent

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.ActivityUserSearchBinding
import com.msg.gcms.extenstion.textChangesToFlow
import com.msg.gcms.ui.adapter.UserSearchAdapter
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserSearchActivity : BaseActivity<ActivityUserSearchBinding>(R.layout.activity_user_search) {

    private val searchViewModel by viewModels<UserViewModel>()

    private lateinit var searchAdapter: UserSearchAdapter

    private val userList = mutableListOf<UserData>()
    private val memberList = mutableListOf<UserData>()

    private val SEARCH_TIMEOUT = 300L

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
        GlobalScope.launch(context = coroutineContext) {
            val editTExtFlow = binding.searchEt.textChangesToFlow()
            editTExtFlow
                .debounce(SEARCH_TIMEOUT)
                .filter {
                    it!!.isNotEmpty()
                }
                .onEach {
                    Log.d("TAG", "observeEditText: $it")
                }
                .launchIn(this)
        }

        // binding.searchEt.addTextChangedListener(object : TextWatcher {
        //     override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //     }
        //
        //     override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //         Log.d("TAG", "onTextChanged: $p0")
        //         p0.let { searchViewModel.searchResult }
        //     }
        //
        //     override fun afterTextChanged(p0: Editable?) {
        //     }
        // })


        // val editTextChangeObservable = binding.searchEt.textChanges()
        //
        // val searchEditTextSubscription: Disposable =
        //     editTextChangeObservable
        //         .debounce(300L, TimeUnit.MILLISECONDS)
        //         .subscribeOn(Schedulers.io())
    }

    // private fun observeResult() {
    //     searchViewModel.searchResult.observe(this) {
    //         searchAdapter.submitList(it)
    //     }
    // }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}