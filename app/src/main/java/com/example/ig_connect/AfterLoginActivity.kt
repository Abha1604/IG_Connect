package com.example.ig_connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.ig_connect.databinding.ActivityAfterLoginBinding
import com.google.android.material.navigation.NavigationView

class AfterLoginActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding:ActivityAfterLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAfterLoginBinding.inflate(/* inflater = */ LayoutInflater.from(this))
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        openFragment(HomeFragment())
        binding.bottomNavigation.background=null
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home->openFragment(HomeFragment())
                R.id.chats->openFragment(Chats_Fragment())
                R.id.community->openFragment(Community_Fragment())
                R.id.profile->openFragment(Profile_Fragment())
            }
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home->openFragment(HomeFragment())
            R.id.community->openFragment(Community_Fragment())
            R.id.chats->openFragment(Chats_Fragment())
            R.id.profile->openFragment(Profile_Fragment())
        }
        return true
    }
//    private fun openFragment(fragment:Fragment) {
//        val fragmentTransaction:FragmentTransaction=fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_container,fragment)
//        fragmentTransaction.commit()
//    }
private fun openFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_container, fragment) // Replace the container with the new fragment
//    transaction.addToBackStack(null)  // Optionally add the transaction to the back stack so the user can navigate back
    transaction.commit()  // Commit the transaction
}




}