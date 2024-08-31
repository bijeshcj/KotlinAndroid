package com.bijesh.coroutine.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bijesh.coroutine.databinding.ActivityStickeyHeaderBinding

class StickyHeaderRecyclerViewActivity : AppCompatActivity() {

    private var binding: ActivityStickeyHeaderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStickeyHeaderBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

    }

}