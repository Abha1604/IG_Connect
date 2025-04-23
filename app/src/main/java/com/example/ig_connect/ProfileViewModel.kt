import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    val profileName = MutableLiveData<String>("Jiya Sharma")
    val profileEmail = MutableLiveData<String>("jiya.sharma@example.com")
    val profileBio = MutableLiveData<String>("CSE Student 2028| App Dev Enthusiast")
    val skills = MutableLiveData<String>("Kotlin, Java, Firebase, UI/UX, Flutter")
    val projectDetails = MutableLiveData<String>("IGConnect App - Platform to connect IGDTUW students alumni")
    val projectTech = MutableLiveData<String>("Tech: Kotlin, Firebase, Firestore")
    val githubLink = MutableLiveData<String>("https://github.com/JiyaSharma")
    val linkedinLink = MutableLiveData<String>("https://linkedin.com/in/jiya-sharma")
}
