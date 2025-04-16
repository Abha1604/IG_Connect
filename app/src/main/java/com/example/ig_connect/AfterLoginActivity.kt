package com.example.ig_connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.example.ig_connect.databinding.ActivityAfterLoginBinding
import com.google.android.material.navigation.NavigationView

class AfterLoginActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityAfterLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterLoginBinding.inflate(LayoutInflater.from(this))
        enableEdgeToEdge()
        setContentView(binding.root)

        // Don’t set padding on the whole view anymore
        // Instead, apply inset padding only to the bottom nav
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigation) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemBars.bottom)
            insets
        }

        // Setup toolbar
        setSupportActionBar(binding.toolbar)

        // Default fragment
        openFragment(HomeFragment())

        // Make bottom nav background transparent if needed
        binding.bottomNavigation.background = null

        // Bottom nav item click handling
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> openFragment(HomeFragment())
                R.id.chats -> openFragment(Chats_Fragment())
                R.id.community -> openFragment(Community_Fragment())
                R.id.profile -> openFragment(Profile_Fragment())
            }
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> openFragment(HomeFragment())
            R.id.community -> openFragment(Community_Fragment())
            R.id.chats -> openFragment(Chats_Fragment())
            R.id.profile -> openFragment(Profile_Fragment())
        }
        return true
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
