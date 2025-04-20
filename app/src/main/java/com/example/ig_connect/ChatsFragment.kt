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

        binding.recyclerViewUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
    }
}
