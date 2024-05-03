package shkonda.artschools.presentation.auth_page.sign_in

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.components.OTFCustom
import shkonda.artschools.core.ui.components.OutBtnCustom

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val signInState by viewModel.signInState.collectAsState()
    val signInInputFieldState by viewModel.signInInputFieldState.collectAsState()
//    val forgotPasswordState by viewModel.forgotPasswordState.collectAsState()

    val activity = LocalContext.current as Activity
//    OnBackPressed(activity = activity, viewModel = viewModel)

    SignInScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        signInState = signInState,
        signInInputFieldState = signInInputFieldState,
//        forgotPasswordState = forgotPasswordState
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SignInScreenContent(
    modifier: Modifier,
    viewModel: SignInViewModel,
    signInState: SignInState,
    signInInputFieldState: SignInInputFieldState,
//    forgotPasswordState: ForgotPasswordState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
        verticalArrangement = Arrangement.Center
    ) {
        /*if (viewModel.showForgotPasswordScreen) {
            ForgotPassword(
                modifier = modifier,
                forgotPasswordState = forgotPasswordState,
                viewModel = viewModel
            )
        } else {
            SignIn(
                modifier = modifier,
                signInState = signInState,
                viewModel = viewModel,
                signInInputFieldState = signInInputFieldState
            )
        }*/

        SignIn(
            modifier = modifier,
            signInState = signInState,
            viewModel = viewModel,
            signInInputFieldState = signInInputFieldState
        )
    }
}

@Composable
private fun SignIn(
    modifier: Modifier,
    signInState: SignInState,
    viewModel: SignInViewModel,
    signInInputFieldState: SignInInputFieldState
) {
    when (signInState) {
        is SignInState.Nothing -> {
            TitleSection(modifier = modifier)
            SignInSection(modifier = modifier, viewModel = viewModel)
            SignInButton(modifier = modifier, viewModel = viewModel)
            RegisterNow(modifier = modifier, onNavigate = { viewModel.navigateSignUpScreen() })
        }
        is SignInState.Loading -> {
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CustomLoadingSpinner()
            }
        }
        is SignInState.Success -> {
            ShowMessage(
                message = "Добро пожаловать!",
                viewModel = viewModel,
                isSignInContent = false
            )
        }
        is SignInState.Error -> {
            ShowMessage(
                message = signInState.errorMessage,
                viewModel = viewModel,
                isSignInContent = true
            )
        }
    }
    ShowInputFieldErrors(signInInputFieldState = signInInputFieldState, viewModel = viewModel)
}

/*@Composable
private fun ForgotPassword(
    modifier: Modifier,
    forgotPasswordState: ForgotPasswordState,
    viewModel: SignInViewModel
) {
    when (forgotPasswordState) {
        is ForgotPasswordState.Nothing -> {
            EnterEmailSection(modifier = modifier, viewModel = viewModel)
        }
        is ForgotPasswordState.Loading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CustomLoadingSpinner()
            }
        }
        is ForgotPasswordState.Success -> {
            ShowMessage(
                message = Messages.RESET_PASS_MAIL,
                viewModel = viewModel,
                isSignInContent = false
            )
            viewModel.resetShowForPas()
        }
        is ForgotPasswordState.Error -> {
            ShowMessage(
                message = forgotPasswordState.errorMessage,
                viewModel = viewModel,
                isSignInContent = false
            )
        }
    }
}*/

@Composable
private fun TitleSection(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello Again!",
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        Text(
            modifier = modifier.padding(top = 16.dp, start = 54.dp, end = 54.dp),
            text = "Welcome back you've been missed\"",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun SignInSection(modifier: Modifier, viewModel: SignInViewModel) {
    SignInInput(modifier = modifier, viewModel = viewModel)
//    ForgotPassword(modifier = modifier, onClick = { viewModel.openForgotPasswordScreen() })
}

@Composable
private fun SignInInput(modifier: Modifier, viewModel: SignInViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OTFCustom(
            modifier = modifier.fillMaxWidth(),
            onValueChanged = { viewModel.updateEmailField(newValue = it) },
            placeHolderText = "Enter email",
            keyboardType = KeyboardType.Email,
            value = viewModel.email,
            isError = viewModel.emailError
        )
        OTFCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = { viewModel.updatePasswordField(newValue = it) },
            placeHolderText = "Password",
            keyboardType = KeyboardType.Password,
            value = viewModel.password,
            isError = viewModel.passwordError
        )
    }
}

/*
* @Composable
private fun ForgotPassword(modifier: Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = "Forgot Password ?",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}*/

@Composable
private fun SignInButton(modifier: Modifier, viewModel: SignInViewModel) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = {
            viewModel.signIn()
            viewModel.resetSignInputState()
        },
        buttonText = "Sign In"
    )
}

@Composable
private fun RegisterNow(modifier: Modifier, onNavigate: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Not a member ?", style = MaterialTheme.typography.body2)
        TextButton(modifier = modifier.padding(start = 1.dp), onClick = onNavigate) {
            Text(
                text = "Register now",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
private fun ShowInputFieldErrors(
    signInInputFieldState: SignInInputFieldState,
    viewModel: SignInViewModel
) {
    when (signInInputFieldState) {
        is SignInInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                signInInputFieldState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetSignInputState()
        }
        is SignInInputFieldState.Nothing -> {}
    }
}

@Composable
private fun ShowMessage(message: String, viewModel: SignInViewModel, isSignInContent: Boolean) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
    if (isSignInContent) {
        viewModel.resetSignInState()
    } else {
//        viewModel.resetForgotPasswordState()
    }
}

//@Composable
//private fun OnBackPressed(activity: Activity, viewModel: SignInViewModel) {
//    BackHandler {
//        if (!viewModel.s)
//    }
//}