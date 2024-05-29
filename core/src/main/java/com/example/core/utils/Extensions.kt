package com.example.core.utils

import android.os.SystemClock
import android.view.View

class SingleClickListener(
    private var defaultInterval: Int = 2000,
    private val onSingleClick: (View) -> Unit,
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSingleClick(v)
    }
}

fun View.setSingleClickListener(onSingleClick: (View) -> Unit) {
    val singleClickListener = SingleClickListener {
        onSingleClick(it)
    }
    setOnClickListener(singleClickListener)
}