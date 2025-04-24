package com.example.ig_connect

import ProfileViewModel
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class EditProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var profileImageView: ImageView
    private val pickImageRequestCode = 1001

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openImagePicker()
        } else {
            Toast.makeText(requireContext(), "Permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                viewModel.profileImageUri.value = uri.toString()
                profileImageView.setImageURI(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)

        val nameInput = view.findViewById<EditText>(R.id.editProfileName)
        val emailInput = view.findViewById<EditText>(R.id.editProfileEmail)
        val bioInput = view.findViewById<EditText>(R.id.editProfileBio)
        val skillsInput = view.findViewById<EditText>(R.id.editSkillsList)
        val projectDetailsInput = view.findViewById<EditText>(R.id.editProjectDetails)
        val projectTechInput = view.findViewById<EditText>(R.id.editProjectTech)
        val githubInput = view.findViewById<EditText>(R.id.editGitHubLink)
        val linkedinInput = view.findViewById<EditText>(R.id.editLinkedInLink)
        val saveBtn = view.findViewById<Button>(R.id.saveProfileButton)

        profileImageView = view.findViewById(R.id.profileImageView)

        // Load saved data into fields
        nameInput.setText(viewModel.profileName.value)
        emailInput.setText(viewModel.profileEmail.value)
        bioInput.setText(viewModel.profileBio.value)
        skillsInput.setText(viewModel.skills.value)
        projectDetailsInput.setText(viewModel.projectDetails.value)
        projectTechInput.setText(viewModel.projectTech.value)
        githubInput.setText(viewModel.githubLink.value)
        linkedinInput.setText(viewModel.linkedinLink.value)

        viewModel.profileImageUri.value?.let {
            profileImageView.setImageURI(Uri.parse(it))
        }

        profileImageView.setOnClickListener {
            checkPermissionAndPickImage()
        }

        saveBtn.setOnClickListener {
            viewModel.profileName.value = nameInput.text.toString()
            viewModel.profileEmail.value = emailInput.text.toString()
            viewModel.profileBio.value = bioInput.text.toString()
            viewModel.skills.value = skillsInput.text.toString()
            viewModel.projectDetails.value = projectDetailsInput.text.toString()
            viewModel.projectTech.value = projectTechInput.text.toString()
            viewModel.githubLink.value = githubInput.text.toString()
            viewModel.linkedinLink.value = linkedinInput.text.toString()

            Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun checkPermissionAndPickImage() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
            openImagePicker()
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }
}


//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.os.Bundle
//import com.example.ig_connect.R
//
//class EditProfileFragment : Fragment() {
//
//    private lateinit var viewModel: ProfileViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
//
//        val nameInput = view.findViewById<EditText>(R.id.editProfileName)
//        val emailInput = view.findViewById<EditText>(R.id.editProfileEmail)
//        val bioInput = view.findViewById<EditText>(R.id.editProfileBio)
//        val skillsInput = view.findViewById<EditText>(R.id.editSkillsList)
//        val projectDetailsInput = view.findViewById<EditText>(R.id.editProjectDetails)
//        val projectTechInput = view.findViewById<EditText>(R.id.editProjectTech)
//        val githubInput = view.findViewById<EditText>(R.id.editGitHubLink)
//        val linkedinInput = view.findViewById<EditText>(R.id.editLinkedInLink)
//
//        val saveBtn = view.findViewById<Button>(R.id.saveProfileButton)
//
//        nameInput.setText(viewModel.profileName.value)
//        emailInput.setText(viewModel.profileEmail.value)
//        bioInput.setText(viewModel.profileBio.value)
//        skillsInput.setText(viewModel.skills.value)
//        projectDetailsInput.setText(viewModel.projectDetails.value)
//        projectTechInput.setText(viewModel.projectTech.value)
//        githubInput.setText(viewModel.githubLink.value)
//        linkedinInput.setText(viewModel.linkedinLink.value)
//
//        saveBtn.setOnClickListener {
//            viewModel.profileName.value = nameInput.text.toString()
//            viewModel.profileEmail.value = emailInput.text.toString()
//            viewModel.profileBio.value = bioInput.text.toString()
//            viewModel.skills.value = skillsInput.text.toString()
//            viewModel.projectDetails.value = projectDetailsInput.text.toString()
//            viewModel.projectTech.value = projectTechInput.text.toString()
//            viewModel.githubLink.value = githubInput.text.toString()
//            viewModel.linkedinLink.value = linkedinInput.text.toString()
//
//            requireActivity().supportFragmentManager.popBackStack()
//        }
//    }
//}
