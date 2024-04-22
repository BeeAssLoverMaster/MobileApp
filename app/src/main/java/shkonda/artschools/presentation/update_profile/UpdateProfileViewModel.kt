package shkonda.artschools.presentation.update_profile

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getToken
import shkonda.artschools.domain.usecase.user.GetUserProfileUseCase
import shkonda.artschools.domain.usecase.user.UpdateProfileUseCase
import shkonda.artschools.domain.usecase.user.UploadProfilePictureUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadProfilePictureUseCase: UploadProfilePictureUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _updateProfileState =
        MutableStateFlow<UpdateProfileState>(UpdateProfileState.Nothing)
    val updateProfileState = _updateProfileState

    private var token: String? = null

    var profilePictureUrl by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    lateinit var body: MultipartBody.Part

    init {
        token = sharedPreferences.getToken()
    }

    fun handleMultipartBody(filePath: String) {
        val file = File(filePath)
        val reqFile = file.asRequestBody("image/${file.extension}".toMediaTypeOrNull())
        body = MultipartBody.Part.createFormData("image", file.name, reqFile)
    }

    fun getUserProfile() = viewModelScope.launch(Dispatchers.IO) {
        getUserProfileUseCase(token = "Bearer $token").collect() { response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    username = response.data.userName

                    profilePictureUrl = response.data.profilePictureUrl
                }
                is Response.Error -> {
                    _updateProfileState.value = UpdateProfileState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun uploadProfilePicture() = viewModelScope.launch(Dispatchers.IO) {
        if (this@UpdateProfileViewModel::body.isInitialized) {
            uploadProfilePictureUseCase(
                token = "Bearer $token",
                filePart = body
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _updateProfileState.value = UpdateProfileState.Loading
                    }
                    is Response.Success -> {
                        _updateProfileState.value = UpdateProfileState.Success
                        getUserProfile()
                    }
                    is Response.Error -> {
                        _updateProfileState.value = UpdateProfileState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }

    fun resetUpdateProfileState() { _updateProfileState.value = UpdateProfileState.Nothing }
}