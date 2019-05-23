package com.binhnk.rxjavaexample.ui.rxjava

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.binhnk.rxjavaexample.R
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.toolbar.*

class PostActivity : AppCompatActivity() {
    private val mContext: Context by lazy { this@PostActivity }

    private val toolbarConstraint1 = ConstraintSet()
    private val toolbarConstraint2 = ConstraintSet()
    private val autoTransition: AutoTransition by lazy {
        AutoTransition()
    }.apply {
        this.value.duration = 3500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        toolbarConstraint1.clone(mContext, R.layout.toolbar)
        toolbarConstraint2.clone(mContext, R.layout.toolbar_alt)

    }
}