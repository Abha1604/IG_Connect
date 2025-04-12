package com.example.ig_connect

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ig_connect.databinding.ActivityProfileDetailsBinding
import java.util.Calendar

class ProfileDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDetailsBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra("USER_EMAIL") == null) {
            Toast.makeText(this, "User data missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        ArrayAdapter.createFromResource(
            this,
            R.array.departments_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDepartment.adapter = adapter
        }

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear..currentYear + 4).toList()
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            years.map { it.toString() }
        ).also { adapter ->
            binding.spinnerYear.adapter = adapter
        }
    }

    private fun setupClickListeners() {
        binding.etStartDate.setOnClickListener { showDatePicker(true) }
        binding.etEndDate.setOnClickListener { showDatePicker(false) }

        binding.cbCurrentlyWorking.setOnCheckedChangeListener { _, isChecked ->
            binding.etEndDate.isEnabled = !isChecked
            if (isChecked) binding.etEndDate.text?.clear()
        }

        binding.btnSubmit.setOnClickListener {
            if (validateInputs()) {
                saveProfileAndProceed()
            }
        }
    }

    private fun showDatePicker(isStartDate: Boolean) {
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val dateStr = "${month + 1}/$day/$year"
                if (isStartDate) {
                    binding.etStartDate.setText(dateStr)
                } else {
                    binding.etEndDate.setText(dateStr)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun validateInputs(): Boolean {
        return when {
            binding.spinnerDepartment.selectedItem == null -> {
                showError("Please select your department")
                false
            }
            binding.etCGPA.text.isNullOrEmpty() -> {
                showError("Please enter your CGPA")
                false
            }
            binding.etLinkedIn.text.isNullOrEmpty() -> {
                showError("Please enter your LinkedIn profile")
                false
            }
            else -> true
        }
    }

    private fun saveProfileAndProceed() {
        // Save profile logic here
        markProfileComplete()

        Toast.makeText(this, "Profile completed!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish() // Properly close ProfileDetailsActivity
    }

    private fun markProfileComplete() {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("PROFILE_COMPLETE", true)
        editor.apply()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}