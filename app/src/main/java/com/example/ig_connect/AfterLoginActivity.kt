package com.example.ig_connect


import ProfileFragment
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.Menu
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
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // Set the title for the toolbar

//        // Set the logo for the toolbar
//        supportActionBar?.setLogo(R.drawable.app_logo) // Use your app logo here
//        supportActionBar?.setDisplayUseLogoEnabled(true) // Enable the logo to act as home icon
//        supportActionBar?.setDisplayShowHomeEnabled(true) // Make the logo visible

        // Default fragment
        openFragment(HomeFragment())

        // Make bottom nav background transparent if needed
        binding.bottomNavigation.background = null

        // Bottom nav item click handling
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> openFragment(HomeFragment())
                R.id.chats -> openFragment(ChatsFragment())
                R.id.community -> openFragment(Community_Fragment())
                R.id.profile -> openFragment(ProfileFragment())
            }
            true
        }
    }

    // Inflate the menu (logout button in the toolbar)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)  // Inflate the logout button menu
        val logoutItem = menu?.findItem(R.id.action_logout)

        // Setting color of the title to white (using a custom TextView approach)
        if (logoutItem != null) {
            val spannable = SpannableString(logoutItem.title)
            spannable.setSpan(ForegroundColorSpan(Color.WHITE), 0, spannable.length, 0)
            logoutItem.title = spannable  // Apply the color change
        }

        return true
    }

    // Handle logout logic when the menu item is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // Logout the user and navigate to the login screen
                logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Function to log out the user and redirect to the login screen (MainActivity)
    private fun logoutUser() {
        // Clear the token from SharedPreferences
        val sharedPref = getSharedPreferences("IGConnectPrefs", MODE_PRIVATE)
        sharedPref.edit().remove("auth_token").apply()

        // Navigate back to the MainActivity (login screen)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Optionally finish the current activity
    }

    // Handle navigation item selections (for bottom navigation or drawer)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> openFragment(HomeFragment())
            R.id.community -> openFragment(Community_Fragment())
            R.id.chats -> openFragment(ChatsFragment())
            R.id.profile -> openFragment(ProfileFragment())
        }
        return true
    }

    // Open the specified fragment in the container
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
