package com.example.core.ui

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import com.example.core.R

class ProgressDialog(private val activity: Activity) : Dialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress_loading)
        setCancelable(false)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        dismiss()
        activity.onBackPressed()
    }
}