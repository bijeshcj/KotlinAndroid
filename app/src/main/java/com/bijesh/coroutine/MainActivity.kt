package com.bijesh.coroutine

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bijesh.coroutine.viewmodels.CoroutineViewModel
import com.bijesh.coroutine.viewmodels.FlowViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       val textView = findViewById<TextView>(R.id.textView)

        val button = findViewById<Button>(R.id.button)

        val mainViewModel : MainViewModel by viewModels()
        val coroutineViewModel : CoroutineViewModel by viewModels()
        val flowViewModel : FlowViewModel by viewModels()

//        mainViewModel.launchCoroutine()

        button.text = "test"

        GlobalScope.launch {
            val f = flowViewModel.simpleFlow()
            f.collect { v ->
                textView.text = v.toString()
            }
            f.collect {
                textView.text = it.toString()
            }
        }

//       GlobalScope.launch {
//           coroutineViewModel.simpleFlow().collect{
//               println("collected $it")
//               button.text = it.toString()
//           }
//       }
//
//       GlobalScope.launch {
//           println(coroutineViewModel.doNetworkCall())
//           println(coroutineViewModel.doNetworkCall2())
//       }

    }
}