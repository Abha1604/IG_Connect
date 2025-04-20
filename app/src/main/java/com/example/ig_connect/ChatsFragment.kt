package com.example.ig_connect

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ig_connect.adapters.UsersAdapter
import com.example.ig_connect.databinding.FragmentChatsBinding
import com.example.ig_connect.models_chat.User

class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var profileImage: ImageView  // Declare it once here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileImage = view.findViewById(R.id.profileImage)  // Find the ImageView

        profileImage.setOnClickListener {
            Toast.makeText(requireContext(), "Profile clicked!", Toast.LENGTH_SHORT).show()
        }

        val users = listOf(
            User().apply {
                id = "1"
<<<<<<< HEAD
                name = "Aayush"
                email = "aayush@example.com"
                profileImage = R.drawable.ic_profile_placeholder

            },
            User().apply {
                id = "2"
                name = "Tanmay"
                email = "tanmay@example.com"
                profileImage = R.drawable.ic_profile_placeholder

            }
        )

        usersAdapter = UsersAdapter(users)
=======
                name = "Diya Kotru"
                email = "diya.kotru@example.com"
            },
            User().apply {
                id = "2"
                name = "Dimpal Agarwal"
                email = "dimpal.agarwal@example.com"
            },
            User().apply {
                id = "3"
                name = "Jaanvi Chaudhary"
                email = "jaanvi.chaudhary@example.com"
            }
        )


        usersAdapter = UsersAdapter(users) { user ->
            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("userName", user.name)
            intent.putExtra("userEmail", user.email)
            startActivity(intent)
        }
>>>>>>> f2d77f5fd85b8ab3b3c9b047e601e996701341a6

        binding.recyclerViewUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
    }
}
