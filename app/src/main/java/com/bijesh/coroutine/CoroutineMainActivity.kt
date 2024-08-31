package com.bijesh.coroutine

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bijesh.coroutine.databinding.ActivityCoroutineMainBinding
import com.bijesh.coroutine.viewmodels.CoroutineViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoroutineMainActivity :  AppCompatActivity() {

    val coroutineViewModel : CoroutineViewModel by viewModels()
    var binding : ActivityCoroutineMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//        GlobalScope.launch {
////            coroutineViewModel.doWorld()
////            coroutineViewModel.doWorldJob()
//            coroutineViewModel.cancelCoroutine()
//        }

        binding?.btnCoroutine?.setOnClickListener{
            GlobalScope.launch {
                coroutineViewModel.cancelCoroutine()
            }
        }


    }

}