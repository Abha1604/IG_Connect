package com.example.ig_connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class EditProfileFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var bioEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // Initialize the views
        nameEditText = view.findViewById(R.id.editProfileName)
        emailEditText = view.findViewById(R.id.editProfileEmail)
        bioEditText = view.findViewById(R.id.editProfileBio)
        saveButton = view.findViewById(R.id.saveProfileButton)

        // Set default text from XML (no need for setText as it's already in XML)

        // Save button click listener
        saveButton.setOnClickListener {
            // Handle save logic here (save changes if necessary)

            // Go back to ProfileFragment after saving changes
            val fragment = Profile_Fragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment) // Replace with container's ID
            transaction.addToBackStack(null) // Add to back stack
            transaction.commit()
        }

        return view
    }
}
