package shkonda.artschools.presentation.auth_page.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.EmailController
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.domain.model.auth.User
import shkonda.artschools.domain.usecase.auth.CreateUserUseCase
import shkonda.artschools.presentation.utils.Messages
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.Nothing)
    val createUserState = _createUserState.asStateFlow()

    private val _registerInputFieldsState =
        MutableStateFlow<RegisterInputFieldState>(RegisterInputFieldState.Nothing)
    val registerInputFieldState = _registerInputFieldsState.asStateFlow()
    var username by mutableStateOf("")
        private set
    var userEmail by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set
    /*var userConfirmPassword by mutableStateOf("")
        private set*/

    var userNameError by mutableStateOf(false)
        private set
    var userEmailError by mutableStateOf(false)
        private set
    var userPasswordError by mutableStateOf(false)
        private set
    var userConfirmPasswordError by mutableStateOf(false)
        private set


    fun updateUserNameField(newValue: String) {
        username = newValue
    }

    fun updateUserEmailField(newValue: String) {
        userEmail = newValue
    }

    fun updateUserPasswordField(newValue: String) {
        userPassword = newValue
    }

    //    fun updateUserConfirmPassword(newValue: String) { userConfirmPassword = newValue }
    fun resetRegisterInpFieldState() {
        _registerInputFieldsState.value = RegisterInputFieldState.Nothing
    }

    fun resetCreateUserState() {
        _createUserState.value = CreateUserState.Nothing
    }

    fun createUser() = viewModelScope.launch(Dispatchers.IO) {
        if (checkRegisterInputField()) {
            createUserUseCase(
                user = User(
                    username = username,
                    email = userEmail,
                    password = userPassword
                )
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _createUserState.value = CreateUserState.Loading
                    }

                    is Response.Success -> {
                        _createUserState.value = CreateUserState.Success
                    }

                    is Response.Error -> {
                        _createUserState.value =
                            CreateUserState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }

    private fun checkRegisterInputField(): Boolean =
        if (checkInputFields() && checkUserEmail() && checkUserPassword() && checkUserName() /*&& confirmUserPassword()*/) {
            _registerInputFieldsState.value = RegisterInputFieldState.Nothing
            true
        } else {
            false
        }

    private fun checkInputFields(): Boolean =
        if (username.isBlank() || userEmail.isBlank() || userPassword.isBlank() /*|| userConfirmPassword.isBlank()*/) {
            _registerInputFieldsState.value =
                RegisterInputFieldState.Error(errorMessage = Messages.FILL)

            userNameError = true
            userEmailError = true
            userPasswordError = true
            userConfirmPasswordError = true

            false
        } else {
            _registerInputFieldsState.value = RegisterInputFieldState.Nothing

            userNameError = false
            userEmailError = false
            userPasswordError = false
            userConfirmPasswordError = false

            true
        }

    //
    private fun checkUserEmail(): Boolean =
        if (EmailController.isEmailType(userEmail)) {
            userEmailError = false
            true
        } else {
            _registerInputFieldsState.value =
                RegisterInputFieldState.Error(errorMessage = Messages.VALID_EMAIL)
            userEmailError = true
            false
        }

    private fun checkUserPassword(): Boolean =
        if (userPassword.length < 6) {
            _registerInputFieldsState.value =
                RegisterInputFieldState.Error(errorMessage = Messages.LENGTH_PASSWORD)
            userPasswordError = true
            false
        } else {
            userPasswordError = false
            true
        }

    private fun checkUserName(): Boolean =
        if (username.length < 3) {
            _registerInputFieldsState.value =
                RegisterInputFieldState.Error(errorMessage = Messages.USER_NAME_LENGTH)
            userNameError = true
            false
        } else {
            userNameError = false
            true
        }

    fun navigateSignInScreen() {
        Navigator.navigate(NavScreen.SignInScreen.route) {}
    }
}
    /*private fun confirmUserPassword(): Boolean =
    if (userPassword == userConfirmPassword) {
        userPasswordError = false
        userConfirmPasswordError = false

        true
    } else {
        _registerInputFieldsState.value = RegisterInputFieldState.Error(errorMessage = Messages.PASSWORD_MATCH)

        userPasswordError = true
        userConfirmPasswordError = true

        false
    }*/
