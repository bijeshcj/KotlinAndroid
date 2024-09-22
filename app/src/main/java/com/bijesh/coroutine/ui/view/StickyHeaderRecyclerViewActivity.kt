package com.bijesh.coroutine.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bijesh.coroutine.databinding.ActivityStickeyHeaderBinding
import com.bijesh.coroutine.ui.view.recycler_view_adapter.StickyHeaderAdapter
import com.bijesh.coroutine.ui.view.recycler_view_adapter.model.Item
import com.bijesh.coroutine.ui.view.viewmodels.NetworkState
import com.bijesh.coroutine.ui.view.viewmodels.StickyHeaderViewModel

class StickyHeaderRecyclerViewActivity : AppCompatActivity() {

    private var binding: ActivityStickeyHeaderBinding? = null
    private val viewModel: StickyHeaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStickeyHeaderBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        mockNetworkProgress()

        initRecyclerView()


    }

    private fun mockNetworkProgress(){
        viewModel.networkState.observe(this) { state ->
            when (state) {
                NetworkState.LOADING -> Toast.makeText(
                    this,
                    "Loading data", Toast.LENGTH_SHORT
                ).show()

                NetworkState.SUCCESS -> Toast.makeText(
                    this,
                    "Success", Toast.LENGTH_SHORT
                ).show()

                NetworkState.FAILURE -> Toast.makeText(
                    this,
                    "Failure", Toast.LENGTH_SHORT
                ).show()

                NetworkState.IDLE -> Toast.makeText(
                    this,
                    "IDLE", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initRecyclerView(){
        val items = listOf(
            Item("Kotlin","Used for Android primarily"),
            Item("Java","Mother of Kotlin")
        )
        val adapter = StickyHeaderAdapter(items)
        binding?.stickyRecyclerView?.let { rv ->
            rv.layoutManager = LinearLayoutManager(this@StickyHeaderRecyclerViewActivity)
            rv.adapter = adapter
        }
    }

}