package com.example.ig_connect

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ig_connect.data.models.ProfileDetailsRequest
import com.example.ig_connect.data.models.ProfileDetailsResponse
import com.example.ig_connect.data.models.UserExperience
import com.example.ig_connect.databinding.ActivityProfileDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class ProfileDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDetailsBinding
    private val calendar = Calendar.getInstance()

    private lateinit var userName: String
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("USER_NAME") ?: ""
        userEmail = intent.getStringExtra("USER_EMAIL") ?: run {
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

        val years = (Calendar.getInstance().get(Calendar.YEAR)..Calendar.getInstance().get(Calendar.YEAR) + 4).toList()
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
                val profileData = createProfileRequest()
                submitUserDetails(profileData)
            }
        }
    }

    private fun createProfileRequest(): ProfileDetailsRequest {
        return ProfileDetailsRequest(
            Year = binding.spinnerYear.selectedItem.toString().toInt(),
            CGPA = binding.etCGPA.text.toString(),
            Bio = binding.etBio.text.toString(),
            Skills = binding.etSkills.text.toString().split(",").map { it.trim() },
            Linkedin = binding.etLinkedIn.text.toString(),
            Experience = listOf(
                UserExperience(
                    position = binding.etPosition.text.toString(),
                    company = binding.etCompany.text.toString(),
                    startDate = binding.etStartDate.text.toString(),
                    endDate = if (binding.cbCurrentlyWorking.isChecked) null else binding.etEndDate.text.toString(),
                    description = binding.etExpDescription.text.toString(),
                    currentlyWorking = binding.cbCurrentlyWorking.isChecked
                )
            ),
            Internships = emptyList(),
            ScholarshipsAcquired = emptyList(),
            Freelancing = emptyList()
        )
    }

    private fun submitUserDetails(profileData: ProfileDetailsRequest) {
        val token = getSharedPreferences("IGConnectPrefs", Context.MODE_PRIVATE)
            .getString("auth_token", null)

        if (token == null) {
            Toast.makeText(this, "No token found. Please login again.", Toast.LENGTH_SHORT).show()
            return
        }

        // Make the request using RetrofitClient
        RetrofitClient.instance.updateProfileDetails("Bearer $token", profileData)
            .enqueue(object : Callback<ProfileDetailsResponse> {
                override fun onResponse(
                    call: Call<ProfileDetailsResponse>,
                    response: Response<ProfileDetailsResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val message = response.body()?.success ?: "Updated successfully!"
                        Toast.makeText(this@ProfileDetailsActivity, message, Toast.LENGTH_SHORT).show()
                        navigateToMain()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@ProfileDetailsActivity, "Error: $errorBody", Toast.LENGTH_LONG).show()
                        Log.e("ProfileUpdate", "Response code: ${response.code()} Body: $errorBody")
                    }
                }

                override fun onFailure(call: Call<ProfileDetailsResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileDetailsActivity, "Network error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileUpdate", "Failure: ${t.message}")
                }
            })
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

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}
