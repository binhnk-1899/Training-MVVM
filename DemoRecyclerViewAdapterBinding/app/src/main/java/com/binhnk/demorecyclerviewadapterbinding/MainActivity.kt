package com.binhnk.demorecyclerviewadapterbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.binhnk.demorecyclerviewadapterbinding.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = UserAdapter()
        recyclerView.adapter = adapter

        binding.viewModel = viewModel

        viewModel.startUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopUpdates()
    }
}
