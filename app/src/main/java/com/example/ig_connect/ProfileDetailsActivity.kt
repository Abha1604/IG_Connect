package com.example.ig_connect

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ig_connect.databinding.ActivityProfileDetailsBinding
import com.example.ig_connect.models.ProfileDetailsRequest
import java.util.Calendar

class ProfileDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDetailsBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("USER_NAME") ?: ""
        val userEmail = intent.getStringExtra("USER_EMAIL") ?: run {
            Toast.makeText(this, "User data missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupUI()
        setupClickListeners(userName, userEmail)
    }

    private fun setupUI() {
        // Department Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.departments_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDepartment.adapter = adapter
        }

        // Year Spinner (Next 5 years)
        val years = (Calendar.getInstance().get(Calendar.YEAR)..Calendar.getInstance().get(Calendar.YEAR) + 4).toList()
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            years.map { it.toString() }
        ).also { adapter ->
            binding.spinnerYear.adapter = adapter
        }
    }

    private fun setupClickListeners(name: String, email: String) {
        binding.etStartDate.setOnClickListener { showDatePicker(true) }
        binding.etEndDate.setOnClickListener { showDatePicker(false) }

        binding.cbCurrentlyWorking.setOnCheckedChangeListener { _, isChecked ->
            binding.etEndDate.isEnabled = !isChecked
            if (isChecked) binding.etEndDate.text?.clear()
        }

        binding.btnSubmit.setOnClickListener {
            if (validateInputs()) {
                val profileData = createProfileRequest(name, email)
                saveProfileLocally(profileData)
                navigateToMain()
            }
        }
    }

    private fun createProfileRequest(name: String, email: String): ProfileDetailsRequest {
        return ProfileDetailsRequest(
            userId = "demo_user_${System.currentTimeMillis()}", // Mock ID
            photoURL = binding.etPhotoUrl.text.toString(),
            skills = binding.etSkills.text.toString().split(",").map { it.trim() },
            bio = binding.etBio.text.toString(),
            department = binding.spinnerDepartment.selectedItem.toString(),
            year = binding.spinnerYear.selectedItem.toString().toInt(),
            cgpa = binding.etCGPA.text.toString(),
            linkedin = binding.etLinkedIn.text.toString(),
            isMentor = binding.switchMentor.isChecked,
            experience = if (binding.etStartDate.text.isNullOrEmpty()) {
                listOf(
                    ProfileDetailsRequest.Experience(
                        position = binding.etPosition.text.toString(),
                        company = binding.etCompany.text.toString(),
                        startDate = binding.etStartDate.text.toString(),
                        endDate = if (binding.cbCurrentlyWorking.isChecked) null else binding.etEndDate.text.toString(),
                        description = binding.etExpDescription.text.toString(),
                        currentlyWorking = binding.cbCurrentlyWorking.isChecked
                    )
                )
            } else emptyList()
        )
    }

    private fun saveProfileLocally(profile: ProfileDetailsRequest) {
        // In a real app, save to SharedPreferences or Room Database
        Toast.makeText(this, "Profile saved locally!", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun showDatePicker(isStartDate: Boolean) {
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val dateStr = "${month + 1}/$day/$year"
                if (isStartDate) binding.etStartDate.setText(dateStr)
                else binding.etEndDate.setText(dateStr)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun validateInputs(): Boolean {
        return when {
            binding.spinnerDepartment.selectedItem == null -> {
                showError("Please select department")
                false
            }
            binding.etCGPA.text.isNullOrEmpty() -> {
                showError("Please enter CGPA")
                false
            }
            binding.etLinkedIn.text.isNullOrEmpty() -> {
                showError("Please enter LinkedIn URL")
                false
            }
            else -> true
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}