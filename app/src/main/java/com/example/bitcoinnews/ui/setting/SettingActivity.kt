package com.example.bitcoinnews.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.bitcoinnews.R
import com.example.bitcoinnews.databinding.ActivitySettingBinding
import com.example.bitcoinnews.utils.Helper
import com.example.core.ui.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private val progress by lazy { ProgressDialog(this@SettingActivity) }

    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarSetting.toolbar)
        observer()
        setupView()
    }

    private fun observer() {
        showProgressDialog(true)
        lifecycleScope.launch {
            binding.appBarSetting.content.apply {
                settingViewModel.getThemeSetting()
                    .observe(this@SettingActivity) { isDarkModeActive: Boolean ->
                        if (isDarkModeActive) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            switchTheme.isChecked = true
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            switchTheme.isChecked = false
                        }
                    }
            }
        }
        showProgressDialog(false)
    }

    private fun setupView() {
        showProgressDialog(true)
        binding.appBarSetting.content.apply {
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }
            val indentation = resources.getDimensionPixelSize(R.dimen._16dp)
            Helper.setIndentedText(
                tvAboutDescription1,
                getString(R.string.setting_text_description_1),
                indentation
            )
            Helper.setIndentedText(
                tvAboutDescription2,
                getString(R.string.setting_text_description_2),
                indentation
            )

            val text = getString(R.string.setting_text_description_3)
            val spannableString = SpannableString(text)

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://newsapi.org/docs/endpoints/everything")
                    )
                    startActivity(intent)
                }

                override fun updateDrawState(ds: android.text.TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.color = ContextCompat.getColor(
                        this@SettingActivity,
                        android.R.color.holo_blue_light
                    )
                }
            }

            val startIndex = text.indexOf("NewsAPI")
            val endIndex = startIndex + "NewsAPI".length

            spannableString.setSpan(
                clickableSpan,
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            Helper.setIndentedText(
                tvAboutDescription3,
                spannableString,
                indentation
            )
            tvAboutDescription3.movementMethod = LinkMovementMethod.getInstance()
        }
        showProgressDialog(false)
    }

    private fun backToPreviousPage() {
        finish()
    }

    private fun showProgressDialog(isLoading: Boolean) {
        if (isLoading) progress.show() else progress.dismiss()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        backToPreviousPage()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progress.isShowing) {
            progress.dismiss()
        }
    }

}