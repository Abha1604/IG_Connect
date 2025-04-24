import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import android.os.Bundle
import android.widget.ImageView
import com.example.ig_connect.EditProfileFragment
import com.example.ig_connect.R

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

        val name = view.findViewById<TextView>(R.id.profileName)
        val email = view.findViewById<TextView>(R.id.profileEmail)
        val bio = view.findViewById<TextView>(R.id.profileBio)
        val skillsList = view.findViewById<TextView>(R.id.skillsList)
        val projectDetails = view.findViewById<TextView>(R.id.projectDetails)
        val projectTech = view.findViewById<TextView>(R.id.projectTech)
        val github = view.findViewById<TextView>(R.id.githubLink)
        val linkedin = view.findViewById<TextView>(R.id.linkedinLink)
        val profileImage = view.findViewById<ImageView>(R.id.profileImage)//new
        val editBtn = view.findViewById<Button>(R.id.editProfileButton)

        viewModel.profileName.observe(viewLifecycleOwner) { name.text = it }
        viewModel.profileEmail.observe(viewLifecycleOwner) { email.text = it }
        viewModel.profileBio.observe(viewLifecycleOwner) { bio.text = it }
        viewModel.skills.observe(viewLifecycleOwner) { skillsList.text = it }
        viewModel.projectDetails.observe(viewLifecycleOwner) { projectDetails.text = it }
        viewModel.projectTech.observe(viewLifecycleOwner) { projectTech.text = it }
        viewModel.githubLink.observe(viewLifecycleOwner) { github.text = "GitHub: $it" }
        viewModel.linkedinLink.observe(viewLifecycleOwner) { linkedin.text = "LinkedIn: $it" }

        viewModel.profileImageUri.observe(viewLifecycleOwner) {
            it?.let { uri ->
                profileImage.setImageURI(Uri.parse(uri))
            }
        }

        editBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EditProfileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
