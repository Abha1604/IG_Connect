package com.example.ig_connect

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.Firebase.firestore.FirebaseFirestore
import com.example.ig_connect.adapters.UsersAdapter
import com.example.ig_connect.databinding.ActivityUsersBinding
import com.example.ig_connect.Listeners.UserListener
import com.example.ig_connect.models_chat.User
import com.example.ig_connect.Utilities.Constants
import com.example.ig_connect.Utilities.PreferenceManager

class UsersActivity : BaseActivity(), UserListener {

    private lateinit var binding: ActivityUsersBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)

        // Set up RecyclerView layout manager
        binding.usersRecycleView.layoutManager = LinearLayoutManager(this)

        setListeners()
        getUsers()
    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getUsers() {
        loading(true)
        val database = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USERS)
            .get()
            .addOnCompleteListener { task ->
                loading(false)
                val currentUserId = preferenceManager.getString(Constants.KEY_USER_ID)
                if (task.isSuccessful && task.result != null) {
                    val users = ArrayList<User>()
                    for (queryDocumentSnapshot in task.result!!) {
                        if (currentUserId == queryDocumentSnapshot.id) continue
                        val user = User().apply {
                            name = queryDocumentSnapshot.getString(Constants.KEY_NAME)
                            email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL)
                            image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE)
                            token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN)
                            id = queryDocumentSnapshot.id
                        }
                        users.add(user)
                    }
                    if (users.isNotEmpty()) {
                        val userAdapter = UsersAdapter(users, this)
                        binding.usersRecycleView.adapter = userAdapter
                        binding.usersRecycleView.visibility = View.VISIBLE
                    } else {
                        showErrorMessage()
                    }
                } else {
                    showErrorMessage()
                }
            }
    }

    private fun showErrorMessage() {
        binding.textErrorMessage.text = "No user available"
        binding.textErrorMessage.visibility = View.VISIBLE
    }

    private fun loading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onUserClick(user: User) {
        val intent = Intent(applicationContext, ChatActivity::class.java)
        intent.putExtra(Constants.KEY_USER, user)
        startActivity(intent)
        finish()
    }
}
