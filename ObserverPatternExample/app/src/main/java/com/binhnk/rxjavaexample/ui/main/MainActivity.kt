package com.binhnk.rxjavaexample.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.binhnk.rxjavaexample.R
import com.binhnk.rxjavaexample.ui.rxjava.RxJavaWithRetrofitActivity

class MainActivity : AppCompatActivity() {

    private val btn_rxjava_with_retrofit_2: Button by lazy { findViewById<Button>(R.id.btn_rxjava_with_retrofit_2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAction()
    }

    private fun initAction() {
        btn_rxjava_with_retrofit_2.setOnClickListener {
            startActivity(Intent(this@MainActivity, RxJavaWithRetrofitActivity::class.java))
        }
    }
}
