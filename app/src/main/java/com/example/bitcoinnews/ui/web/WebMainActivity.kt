package com.example.bitcoinnews.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.bitcoinnews.databinding.ActivityWebBinding
import com.example.core.ui.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding

    private val progress by lazy { ProgressDialog(this@WebMainActivity) }

    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarWeb.toolbar)

        url = intent.getStringExtra(EXTRA_DATA) ?: ""
        setupView(url)
    }

    private fun setupView(url: String) {
        url.let {
            showProgressDialog(true)
            setJavascriptEnabled()
            binding.appBarWeb.content.webview.apply {
                settings.apply {
                    loadsImagesAutomatically = true
                    domStorageEnabled = true
                    builtInZoomControls = true
                    displayZoomControls = false
                }
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                loadUrl(it)
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        showProgressDialog(true)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        showProgressDialog(false)
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.appBarWeb.content.webview.canGoBack()) {
                    binding.appBarWeb.content.webview.goBack()
                } else {
                    finish()
                }
            }
        })
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setJavascriptEnabled() {
        binding.appBarWeb.content.webview.settings.javaScriptEnabled = true
    }

    private fun showProgressDialog(isLoading: Boolean) {
        if (isLoading) progress.show() else progress.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progress.isShowing) {
            progress.dismiss()
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}