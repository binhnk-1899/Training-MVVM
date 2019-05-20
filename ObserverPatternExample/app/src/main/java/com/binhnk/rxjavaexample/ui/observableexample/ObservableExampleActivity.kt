package com.binhnk.rxjavaexample.ui.observableexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binhnk.rxjavaexample.models.User
import com.binhnk.rxjavaexample.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_example_observable.*
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import java.util.*


@SuppressLint("SetTextI18n")
class ObservableExampleActivity : AppCompatActivity() {

    val user = User("Cristiano Ronaldo")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.binhnk.rxjavaexample.R.layout.activity_example_observable)

        btn_from.setOnClickListener {
            Observable.from(arrayOf(1, 2, 3, 4, 5)).subscribe { t: Int? ->
                run {
                    if (t != null) {
                        tv_from.text = tv_from.text.toString() + "onNext: $t\n"
                    }
                }
            }
        }

        btn_just.setOnClickListener {
            Observable.just(arrayOf(1, 2, 3, 4, 5)).subscribe { t: Array<Int>? ->
                run {
                    if (t != null)
                        tv_just.text = "onNext: ${Arrays.toString(t)}"
                }
            }
        }

        btn_refer.setOnClickListener {
            user.name = "Leo Messi"
            val userObservable: Observable<User> = Observable.defer { Observable.just(user) }
            userObservable.subscribe { t: User ->
                run {
                    tv_refer.text = t.name
                }
            }
        }

        btn_clear_from.setOnClickListener {
            tv_from.text = null
        }

        btn_clear_just.setOnClickListener {
            tv_just.text = null
        }

        btn_clear_refer.setOnClickListener {
            tv_refer.text = null
        }

        btn_immediate.setOnClickListener {
            subscribe(Schedulers.immediate())
        }
        btn_trampoline.setOnClickListener {
            subscribe(Schedulers.trampoline())
        }
        btn_newThread.setOnClickListener {
            subscribe(Schedulers.newThread())
        }
        btn_computation.setOnClickListener {
            subscribe(Schedulers.computation())
        }
        btn_io.setOnClickListener {
            subscribe(Schedulers.io())
        }
        btn_observe_example.setOnClickListener {
            getANumberObservable() // this will run on mainThread
                .observeOn(Schedulers.newThread()) // change thread
                .map { integer -> // run on new thread 1
                    Log.e(Utils.TAG, "Operator thread: ${Thread.currentThread().name}")
                    integer
                }
                .observeOn(Schedulers.io()) // change thread
                .subscribe { // run on new thread 2
                    Log.e(Utils.TAG, "Subscriber thread: ${Thread.currentThread().name}")
                }
        }
        btn_rxandroid_example.setOnClickListener {
            getUserObservable() // this will run on mainThread
                .map { integer -> // run on new thread 1
                    Log.e(Utils.TAG, "Operator thread: ${Thread.currentThread().name}")
                    integer
                }
                .observeOn(AndroidSchedulers.mainThread()) // change thread
                .subscribe { // run on UI thread
                    Log.e(Utils.TAG, "Subscriber thread: ${Thread.currentThread().name}")
                    Toast.makeText(this@ObservableExampleActivity, "${user.name}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getUserObservable() : io.reactivex.Observable<User>{
        return io.reactivex.Observable.just(user)
    }

    private fun getANumberObservable(): Observable<Int> {
        return Observable.defer {
            Log.e(Utils.TAG, "Observable thread: ${Thread.currentThread().name}")
            Observable.just(1)
        }
    }

    private fun subscribe(s: Scheduler) {
        getANumberObservable()
            .observeOn(s)
            .map { integer ->
                Log.e(Utils.TAG, "Operator thread: ${Thread.currentThread().name}")
                integer
            }
            .subscribe {
                Log.e(Utils.TAG, "Subscriber thread: ${Thread.currentThread().name}")
            }
    }
}