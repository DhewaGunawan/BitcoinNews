package com.example.bitcoinnews.ui.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.bitcoinnews.R
import com.example.bitcoinnews.databinding.ActivityMainBinding
import com.example.bitcoinnews.ui.home.HomeFragment
import com.example.bitcoinnews.ui.setting.SettingActivity
import com.example.bitcoinnews.utils.Helper
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        Log.d("Testing4", "MAIN ACTIVITY ON CREATE")

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment())
                .commit()
            supportActionBar?.title = getString(R.string.app_name)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("Testing4", "handleOnBackPressed: CLICKED")
                finishAffinity()
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val title = getString(R.string.app_name)
        when (item.itemId) {
            R.id.nav_home -> {
                Helper.setSuccessSnackBar(
                    binding.root.context,
                    binding.root,
                    getString(R.string.util_already_open_page),
                )
            }

            R.id.nav_favorite -> {
                val uri = Uri.parse("bitcoinnews://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }

        supportActionBar?.title = title

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_page -> {
                Log.d("Testing4", "onOptionsItemSelected: ${item.itemId}")
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}