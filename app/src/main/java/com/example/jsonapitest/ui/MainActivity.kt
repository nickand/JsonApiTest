package com.example.jsonapitest.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.jsonapitest.R
import com.example.jsonapitest.ui.favorite.FavoriteFragment
import com.example.jsonapitest.ui.main.PostFragment
import com.example.jsonapitest.ui.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        initHomeFragment()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_dashboard -> {
                    initHomeFragment()
                }
                R.id.action_favorite -> {
                    initFavoriteFragment()
                }
            }
            true
        }
    }

    private fun initFavoriteFragment() {
        Navigation.addFragment(supportFragmentManager, R.id.fragmentContainer, FavoriteFragment())
    }

    private fun initHomeFragment() {
        Navigation.replaceFragment(supportFragmentManager, R.id.fragmentContainer, PostFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_refresh -> {
                initHomeFragment()
                true
            }
            else -> {
                false
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    companion object {
        private val CLASS_TAG = MainActivity::class.java.simpleName
    }
}
