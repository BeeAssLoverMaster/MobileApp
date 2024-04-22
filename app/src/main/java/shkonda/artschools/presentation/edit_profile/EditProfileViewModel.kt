package shkonda.artschools.presentation.edit_profile

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import shkonda.artschools.core.common.removeToken
import shkonda.artschools.core.navigation.EditProfileScreenArgs
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.presentation.utils.EditProfileScreenPreferencesNames
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var username by mutableStateOf("")
        private set

    var profilePicture by mutableStateOf("")
        private set

    init {
//        username = savedStateHandle[EditProfileScreenArgs.USERNAME] ?: "-"
//        profilePicture = savedStateHandle[EditProfileScreenArgs.USER_PROFILE_IMG] ?: ""

        username = sharedPreferences.getString("username", "empty") ?: "empty"
        profilePicture = sharedPreferences.getString("profilePicture", "empty") ?: "empty"
    }

    fun setPreferenceOnClick(preferenceName: String) {
        when(preferenceName) {
            EditProfileScreenPreferencesNames.LANGUAGE -> {}
            EditProfileScreenPreferencesNames.DARK_MODE -> {}
            EditProfileScreenPreferencesNames.LOG_OUT -> {
                with(sharedPreferences.edit()) {
                    removeToken()
                }
                Navigator.navigate(NavScreen.SignInScreen.route) {
                    popUpTo(0)
                }
            }
            else -> {}
        }
    }
}