package com.binhnk.rxjavaexample

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mTimeData = TimeData(System.currentTimeMillis())

        supportFragmentManager.beginTransaction()
                .add(R.id.layout_container_1, OneFragment(mTimeData))
                .add(R.id.layout_container_2, TwoFragment())
                .commit()

        Handler().postDelayed({
            mTimeData.setMeasurements(System.currentTimeMillis())
        }, 5000)
    }

    private fun startRStream() {
        val list = listOf("0", "1", "2", "3", "4")
        list.toObservable().subscribeBy(
            onError = {it.printStackTrace()},
            onNext = { println(it)},
            onComplete = { println("Completed")}
        )
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
