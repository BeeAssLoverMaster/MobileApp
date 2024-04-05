package shkonda.artschools.presentation.sign_up

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.components.OTFCustom
import shkonda.artschools.core.ui.components.OutBtnCustom
import shkonda.artschools.presentation.utils.Messages

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val createUserState by viewModel.createUserState.collectAsState()
    val registerInputFieldState by viewModel.registerInputFieldState.collectAsState()

//    OnBackPressed(targetRoute = NavScreen.SignInScreen.route)

    RegisterScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        registerInputFieldState = registerInputFieldState,
        createUserState = createUserState
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreenContent(
    modifier: Modifier,
    viewModel: SignUpViewModel,
    registerInputFieldState: RegisterInputFieldState,
    createUserState: CreateUserState
) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            when (createUserState) {
                is CreateUserState.Nothing -> {
                    TitleSection(modifier = modifier)
                    InputSection(modifier = modifier, viewModel = viewModel)
                    RegisterButton(modifier = modifier, viewModel = viewModel)
                }

                is CreateUserState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        CustomLoadingSpinner()
                    }
                }

                is CreateUserState.Success -> {
                    ShowMessage(message = Messages.USER_CREATE_SUCCESS)
                }

                is CreateUserState.Error -> {
                    ShowMessage(
                        message = createUserState.errorMessage,
                        createUserState = createUserState,
                        onReset = { viewModel.resetCreateUserState() }
                    )
                }
            }
            ShowInputFieldErrors(
                registerInputFieldState = registerInputFieldState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun TitleSection(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        text = "ПРИСОЕДИНЯЙСЯ",
//        style =
        textAlign = TextAlign.Start,
//        color =
    )
}

@Composable
fun InputSection(
    modifier: Modifier,
    viewModel: SignUpViewModel
) {
    OTFCustom(
        modifier = Modifier.fillMaxWidth(),
        value = viewModel.userName,
        onValueChanged = { viewModel.updateUserNameField(newValue = it) },
        placeHolderText = "Логин",
        isError = viewModel.userNameError
    )
    OTFCustom(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = viewModel.userEmail,
        keyboardType = KeyboardType.Email,
        onValueChanged = { viewModel.updateUserEmailField(newValue = it) },
        placeHolderText = "Email",
        isError = viewModel.userEmailError
    )
    OTFCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = viewModel.userPassword,
        onValueChanged = { viewModel.updateUserPasswordField(newValue = it) },
        placeHolderText = "Пароль",
        keyboardType = KeyboardType.Password,
        isError = viewModel.userPasswordError
    )
    /*OTFCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = viewModel.userConfirmPassword,
        onValueChanged = { viewModel.updateUserConfirmPassword(newValue = it) },
        placeHolderText = "Подтверждение пароля",
        keyboardType = KeyboardType.Password,
        isError = viewModel.userConfirmPasswordError
    )*/
}

@Composable
fun RegisterButton(
    modifier: Modifier,
    viewModel: SignUpViewModel
) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = { viewModel.createUser() },
        buttonText = "Регистрация"
    )
}

@Composable
private fun ShowInputFieldErrors(
    registerInputFieldState: RegisterInputFieldState,
    viewModel: SignUpViewModel
){
    when (registerInputFieldState) {
        is RegisterInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                registerInputFieldState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetRegisterInpFieldState()
        }

        is RegisterInputFieldState.Nothing -> {}
    }
}

@Composable
private fun ShowMessage(
    message: String,
    createUserState: CreateUserState = CreateUserState.Nothing,
    onReset: () -> Unit = {}
) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
    if (createUserState is CreateUserState.Error) {
        onReset()
    }
}