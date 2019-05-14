package com.binhnk.retrofitwithroom.utils

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class CustomSwipeRefreshLayout: SwipeRefreshLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun setRefreshing(refreshing: Boolean) {
        post { super@CustomSwipeRefreshLayout.setRefreshing(refreshing) }
    }
}