import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.os.Bundle
import com.example.ig_connect.R

class EditProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

        val nameInput = view.findViewById<EditText>(R.id.editProfileName)
        val emailInput = view.findViewById<EditText>(R.id.editProfileEmail)
        val bioInput = view.findViewById<EditText>(R.id.editProfileBio)
        val skillsInput = view.findViewById<EditText>(R.id.editSkillsList)
        val projectDetailsInput = view.findViewById<EditText>(R.id.editProjectDetails)
        val projectTechInput = view.findViewById<EditText>(R.id.editProjectTech)
        val githubInput = view.findViewById<EditText>(R.id.editGitHubLink)
        val linkedinInput = view.findViewById<EditText>(R.id.editLinkedInLink)
        val saveBtn = view.findViewById<Button>(R.id.saveProfileButton)

        nameInput.setText(viewModel.profileName.value)
        emailInput.setText(viewModel.profileEmail.value)
        bioInput.setText(viewModel.profileBio.value)
        skillsInput.setText(viewModel.skills.value)
        projectDetailsInput.setText(viewModel.projectDetails.value)
        projectTechInput.setText(viewModel.projectTech.value)
        githubInput.setText(viewModel.githubLink.value)
        linkedinInput.setText(viewModel.linkedinLink.value)

        saveBtn.setOnClickListener {
            viewModel.profileName.value = nameInput.text.toString()
            viewModel.profileEmail.value = emailInput.text.toString()
            viewModel.profileBio.value = bioInput.text.toString()
            viewModel.skills.value = skillsInput.text.toString()
            viewModel.projectDetails.value = projectDetailsInput.text.toString()
            viewModel.projectTech.value = projectTechInput.text.toString()
            viewModel.githubLink.value = githubInput.text.toString()
            viewModel.linkedinLink.value = linkedinInput.text.toString()

            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
