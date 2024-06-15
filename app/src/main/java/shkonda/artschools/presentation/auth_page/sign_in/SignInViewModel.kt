package shkonda.artschools.presentation.auth_page.sign_in

import android.content.SharedPreferences
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
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.storeToken
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.usecase.auth.SignInUseCase
import shkonda.artschools.presentation.auth_page.sign_in.states.SignInInputFieldState
import shkonda.artschools.presentation.utils.Messages
import javax.inject.Inject

// Аннотация @HiltViewModel указывает на то, что данный класс является ViewModel,
// который использует Hilt для внедрения зависимостей
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _signInState = MutableStateFlow<SignInState>(SignInState.Nothing)
    val signInState = _signInState.asStateFlow()

    private val _signInInputFieldState =
        MutableStateFlow<SignInInputFieldState>(SignInInputFieldState.Nothing)
    val signInInputFieldState = _signInInputFieldState.asStateFlow()

    var email by mutableStateOf("user@gmail.com")
        private set
    var password by mutableStateOf("123456")
        private set
    var emailError by mutableStateOf(false)
        private set
    var passwordError by mutableStateOf(false)
        private set

    // Функция signIn, выполняющая операцию входа.
    // Запускает корутину в viewModelScope с использованием Dispatchers.IO
    fun signIn() = viewModelScope.launch(Dispatchers.IO) {
        if (checkInputFields()) {
            signInUseCase(
                login = Login(email = email, password = password)
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _signInState.value = SignInState.Loading
                    }
                    is Response.Success -> {
                        _signInState.value = SignInState.Success(data = response.data)
                        with(sharedPreferences.edit()) {
                            storeToken(token = response.data.token)
                        }
                        Navigator.navigate(NavScreen.HomeScreen.route) {}
                    }

                    is Response.Error -> {
                        _signInState.value = SignInState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }
    // Функция для обновления поля email
    fun updateEmailField(newValue: String) { email = newValue }
    // Функция для обновления поля пароля
    fun updatePasswordField(newValue: String) { password = newValue }
    // Функция для сброса состояния полей ввода
    fun resetSignInputState() { _signInInputFieldState.value = SignInInputFieldState.Nothing }
    // Функция для сброса состояния входа
    fun resetSignInState() { _signInState.value = SignInState.Nothing }
    // Приватная функция для проверки корректности полей ввода
    private fun checkInputFields(): Boolean =
        if (email.isBlank() && password.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            emailError = true
            passwordError = true
            false
        } else if (email.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            emailError = true
            passwordError = false
            false
        } else if (password.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            passwordError = true
            emailError = false
            false
        } else {
            emailError = false
            passwordError = false
            true
        }

    // Функция для перехода на экран регистрации
    fun navigateSignUpScreen() {
        Navigator.navigate(NavScreen.SignUpScreen.route) {}
    }
}