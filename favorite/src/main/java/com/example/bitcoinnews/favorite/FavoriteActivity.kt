package com.example.bitcoinnews.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoinnews.di.FavoriteModuleDependencies
import com.example.bitcoinnews.favorite.databinding.ActivityFavoriteBinding
import com.example.bitcoinnews.ui.detail.DetailActivity
import com.example.bitcoinnews.ui.setting.SettingActivity
import com.example.bitcoinnews.utils.Helper
import com.example.core.ui.NewsAdapter
import com.example.core.ui.ProgressDialog
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private val progress by lazy { ProgressDialog(this@FavoriteActivity) }

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarFavorite.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarFavorite.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        supportActionBar?.title = getString(R.string.title_favorite)

        newsAdapter = NewsAdapter()
        observer()
        setupView()
        setupClickListener()
    }

    private fun observer() {
        showProgressDialog(true)
        favoriteViewModel.favoriteNews.observe(this@FavoriteActivity) { dataFavorite ->
            newsAdapter.submitList(dataFavorite)
            binding.appBarFavorite.content.viewEmpty.root.visibility =
                if (dataFavorite.isNotEmpty()) View.GONE else View.VISIBLE
        }
        showProgressDialog(false)
    }

    private fun setupView() {
        showProgressDialog(true)
        showShimmer(true)
        with(binding.appBarFavorite.content.rvNews) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    binding.appBarFavorite.content.fabScrollToTop.isVisible =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() >= 3
                }
            })
        }
        showShimmer(false)
        showProgressDialog(false)
    }

    private fun setupClickListener() {
        showProgressDialog(true)
        binding.appBarFavorite.content.apply {
            fabScrollToTop.setOnClickListener {
                rvNews.smoothScrollToPosition(0)
            }
        }

        newsAdapter.onItemClick = { selectedData ->
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        showProgressDialog(false)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                finish()
            }

            R.id.nav_favorite -> {
                Helper.setSuccessSnackBar(
                    binding.root.context,
                    binding.root,
                    getString(R.string.util_already_open_page),
                )
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_page -> {
                Log.d("Testing4", "onOptionsItemSelected: ${item.itemId}")
                val intent = Intent(this@FavoriteActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showShimmer(isShimmering: Boolean) {
        binding.appBarFavorite.content.progressBar.visibility =
            if (isShimmering) View.VISIBLE else View.GONE
    }

    private fun showProgressDialog(isLoading: Boolean) {
        if (isLoading) progress.show() else progress.dismiss()
    }
}