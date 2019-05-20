package com.binhnk.rxjavaexample.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.binhnk.rxjavaexample.R
import com.binhnk.rxjavaexample.ui.observableexample.ObservableExampleActivity
import com.binhnk.rxjavaexample.ui.rxjava.PostActivity

class MainActivity : AppCompatActivity() {

    private val btn_rxjava_with_retrofit_2: Button by lazy { findViewById<Button>(R.id.btn_rxjava_with_retrofit_2) }
    private val btn_observable_example: Button by lazy { findViewById<Button>(R.id.btn_observable_example) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAction()
    }

    private fun initAction() {
        btn_rxjava_with_retrofit_2.setOnClickListener {
            startActivity(Intent(this@MainActivity, PostActivity::class.java))
        }

        btn_observable_example.setOnClickListener {
            startActivity(Intent(this@MainActivity, ObservableExampleActivity::class.java))
        }
    }
}
