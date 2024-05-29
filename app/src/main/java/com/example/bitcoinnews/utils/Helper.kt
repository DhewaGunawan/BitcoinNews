package com.example.bitcoinnews.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.LeadingMarginSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.bitcoinnews.R
import com.example.core.utils.setSingleClickListener
import com.google.android.material.snackbar.Snackbar

object Helper {

    fun setSuccessSnackBar(
        context: Context, view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT
    ) {
        val snackBar = Snackbar.make(context, view, message, duration)
        snackBar.view.apply {
            setSingleClickListener { snackBar.dismiss() }
            setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }
        snackBar.setTextColor(ContextCompat.getColor(context, R.color.white)).show()
    }

    fun setIndentedText(textView: TextView, text: CharSequence, firstLineIndentation: Int) {
        val spannableStringBuilder = if (text is SpannableString) {
            SpannableStringBuilder(text)
        } else {
            SpannableStringBuilder(text.toString())
        }

        spannableStringBuilder.setSpan(
            MyLeadingMarginSpan(firstLineIndentation),
            0,
            spannableStringBuilder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableStringBuilder
    }


    class MyLeadingMarginSpan(private val margin: Int) : LeadingMarginSpan {
        override fun getLeadingMargin(first: Boolean): Int {
            return if (first) margin else 0
        }

        override fun drawLeadingMargin(
            c: Canvas,
            p: Paint,
            x: Int,
            dir: Int,
            top: Int,
            baseline: Int,
            bottom: Int,
            text: CharSequence?,
            start: Int,
            end: Int,
            first: Boolean,
            layout: Layout?
        ) {
        }
    }
}