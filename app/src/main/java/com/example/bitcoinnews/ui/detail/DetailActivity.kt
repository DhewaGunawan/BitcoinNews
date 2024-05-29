package com.example.bitcoinnews.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.bitcoinnews.R
import com.example.bitcoinnews.databinding.ActivityDetailBinding
import com.example.bitcoinnews.ui.web.WebMainActivity
import com.example.bitcoinnews.utils.Helper
import com.example.core.domain.model.News
import com.example.core.ui.ProgressDialog
import com.example.core.utils.setSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val progress by lazy { ProgressDialog(this@DetailActivity) }

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail: News? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, News::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        showDetail(detail)
    }

    private fun showDetail(detail: News?) {
        showProgressDialog(true)
        detail?.let {
            supportActionBar?.title = detail.title
            Log.d("Testing4", "showDetail : $detail")
            binding.content.apply {
                tvDetailTitle.text = detail.title
                val inputDateTime = "2024-05-20T13:20:00Z"
                val zonedDateTime = ZonedDateTime.parse(inputDateTime)
                val outputFormatter =
                    DateTimeFormatter.ofPattern("dd MMMM yyyy | hh.mm a", Locale.getDefault())
                val formattedDateTime = zonedDateTime.format(outputFormatter)
                tvDetailPublish.text = getString(
                    R.string.detail_text_published,
                    formattedDateTime
                )
                tvDetailAuthor.text = getString(
                    R.string.detail_text_author,
                    detail.author
                )

                val indentation = resources.getDimensionPixelSize(R.dimen._16dp)
                if (detail.description.isNullOrEmpty()) {
                    Helper.setIndentedText(
                        tvDetailDescription,
                        getString(R.string.util_no_data),
                        indentation
                    )
                } else {
                    val description = detail.description
                    description?.let {
                        val result = if (it.endsWith("...")) {
                            val lastDotIndex =
                                description.substring(0, description.length - 3).lastIndexOf('.')
                            if (lastDotIndex != -1) {
                                description.substring(0, lastDotIndex + 1)
                            } else {
                                description
                            }
                        } else if (description.endsWith("…")) {
                            val lastDotIndex =
                                description.substring(0, description.length - 1).lastIndexOf('.')
                            if (lastDotIndex != -1) {
                                description.substring(0, lastDotIndex + 1)
                            } else {
                                description
                            }
                        } else {
                            description
                        }

                        Helper.setIndentedText(
                            tvDetailDescription,
                            result,
                            indentation
                        )
                    }
                }

                btnToWeb.setSingleClickListener {
                    val intent = Intent(this@DetailActivity, WebMainActivity::class.java)
                    intent.putExtra(WebMainActivity.EXTRA_DATA, detail.url)
                    startActivity(intent)
                }
            }
            if (detail.urlToImage.isNullOrEmpty()) {
                Glide.with(this@DetailActivity)
                    .load(R.drawable.no_image)
                    .into(binding.ivDetailImage)
            } else {
                Glide.with(this@DetailActivity)
                    .load(detail.urlToImage)
                    .into(binding.ivDetailImage)
            }
            var statusFavorite = detail.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setSingleClickListener {
                statusFavorite = !statusFavorite
                detailViewModel.setFavoriteNews(detail, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
        showProgressDialog(false)
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        } else {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_not_favorite
                )
            )
        }
    }

    private fun showProgressDialog(isLoading: Boolean) {
        if (isLoading) progress.show() else progress.dismiss()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
