package shkonda.artschools.presentation.auth_page.sign_in

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import shkonda.artschools.R
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.components.OTFCustom
import shkonda.artschools.core.ui.theme.CustomBlue
import shkonda.artschools.core.ui.theme.CustomBrown
import shkonda.artschools.presentation.auth_page.sign_in.states.SignInInputFieldState


@Composable /* Создание экрана входа в систему */
fun SignInScreen(modifier: Modifier = Modifier, viewModel: SignInViewModel = hiltViewModel()) {
    /* Получение состояния входа в систему */
    val signInState by viewModel.signInState.collectAsState()
    val signInInputFieldState by viewModel.signInInputFieldState.collectAsState()
    /* Отображение контента экрана входа в систему */
    SignInScreenContent(modifier = modifier, viewModel = viewModel,
        signInState = signInState, signInInputFieldState = signInInputFieldState)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable // Функция для отображения контента экрана входа в систему
private fun SignInScreenContent(
    modifier: Modifier, viewModel: SignInViewModel,
    signInState: SignInState, signInInputFieldState: SignInInputFieldState,
) {
    // Отображение заднего фона и формы входа
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background), contentDescription = null,
                modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f))
            )
            SignIn(
                modifier = modifier, signInState = signInState,
                viewModel = viewModel, signInInputFieldState = signInInputFieldState
            )
        }
    }
}


@Composable // Функция для отображения формы входа
private fun SignIn(modifier: Modifier, signInState: SignInState,
                   viewModel: SignInViewModel, signInInputFieldState: SignInInputFieldState
) {
    // Отображение различных состояний формы входа (ничего, загрузка, успешный вход, ошибка)
    when (signInState) {
        is SignInState.Nothing -> {
            Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                TitleSection(modifier = modifier)
                SignInSection(modifier = modifier, viewModel = viewModel)
                SignInButton(modifier = modifier, viewModel = viewModel)
                RegisterNow(modifier = modifier, onNavigate = { viewModel.navigateSignUpScreen() })
            }
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


@Composable // Функция для отображения заголовка на экране входа
private fun TitleSection(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val titleFontSize = if (screenWidth <= 360.dp) 25.sp else 35.sp
    val subtitleFontSize = if (screenWidth <= 360.dp) 22.sp else 30.sp
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "И снова - здравствуйте!",
            style = TextStyle(color = Color.White, fontSize = titleFontSize, fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Добро подаловать обратно\nмы скучали по вам!",
            style = TextStyle(
                color = Color.White.copy(alpha = 0.8f),
                fontSize = subtitleFontSize,
                textAlign = TextAlign.Center
            )
        )
    }
}


@Composable // Функция для отображения формы ввода на экране входа
private fun SignInSection(modifier: Modifier, viewModel: SignInViewModel) {
    // Отображение полей ввода (email, пароль)
    Column(modifier = modifier.fillMaxWidth().padding(bottom = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        SignInInput(modifier = modifier, viewModel = viewModel)
    }
}


@Composable // Функция для отображения полей ввода (Email и пароль) на экране входа
private fun SignInInput(modifier: Modifier, viewModel: SignInViewModel) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поле для ввода Email
        OTFCustom(
            modifier = modifier.padding(vertical = 16.dp), onValueChanged = { viewModel.updateEmailField(newValue = it) },
            placeHolderText = "E-mail", keyboardType = KeyboardType.Email,
            value = viewModel.email, isError = viewModel.emailError
        )
        // Поле для ввода пароля
        OTFCustom(
            modifier = modifier.padding(bottom = 0.dp), onValueChanged = { viewModel.updatePasswordField(newValue = it) },
            placeHolderText = "Пароль", keyboardType = KeyboardType.Password,
            value = viewModel.password, isError = viewModel.passwordError
        )
    }
}


@Composable // Функция для отображения кнопки входа
private fun SignInButton(modifier: Modifier, viewModel: SignInViewModel) {
    Button(
        onClick = {
            viewModel.signIn() // Вызов функции входа при нажатии кнопки
            viewModel.resetSignInputState() // Сброс состояния полей ввода
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent // Фон кнопки прозрачный
        ),
        modifier = modifier
            .height(64.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp), // Устанавливаем размер кнопки 64.dp
        contentPadding = PaddingValues(0.dp) // Устанавливаем padding 0, чтобы избежать дополнительного отступа
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(CustomBlue.copy(alpha = 0.75f), CustomBrown.copy(alpha = 0.75f))
                    ), shape = MaterialTheme.shapes.small
                ).border(width = 1.5f.dp, color = Color.White, shape = MaterialTheme.shapes.small)
        ) {
            Text(
                text = "Войти", color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable // Функция для отображения ссылки на регистрацию нового аккаунта
private fun RegisterNow(modifier: Modifier, onNavigate: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Нет аккаунта?",
            style = MaterialTheme.typography.body2.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextButton(
            modifier = modifier.padding(start = 1.dp),
            onClick = onNavigate,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Зарегистрируйся сейчас",
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}

@Composable // Функция для отображения сообщений об ошибках ввода
private fun ShowInputFieldErrors(signInInputFieldState: SignInInputFieldState, viewModel: SignInViewModel) {
    when (signInInputFieldState) {
        is SignInInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                signInInputFieldState.errorMessage, // Сообщение об ошибке
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetSignInputState() // Сброс состояния полей ввода
        }

        is SignInInputFieldState.Nothing -> {}
    }
}

@Composable //Функция для демонстрации сообщения
private fun ShowMessage(message: String, viewModel: SignInViewModel, isSignInContent: Boolean) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
    if (isSignInContent) {
        viewModel.resetSignInState()
    }
}

//@Composable
//private fun OnBackPressed(activity: Activity, viewModel: SignInViewModel) {
//    BackHandler {
//        if (!viewModel.s)
//    }
//}

@Preview(showBackground = true, name = "Small Phone Preview", widthDp = 320, heightDp = 568)
@Preview(showBackground = true, name = "Medium Phone Preview", widthDp = 360, heightDp = 640)
@Preview(showBackground = true, name = "Large Phone Preview", widthDp = 411, heightDp = 731)
@Preview(showBackground = true, name = "Extra Large Phone Preview", widthDp = 480, heightDp = 800)
@Composable
private fun SignInPrev() {
    SingInFakeContent()
}

@Composable
fun SingInFakeContent(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Полупрозрачный черный цвет
            )
            FakeSignIn()
        }
    }

}

@Composable
fun FakeSignIn(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        FakeTitleSection(modifier = Modifier.padding(bottom = 0.dp))
        FakeSignInSection(modifier = Modifier.padding(bottom = 8.dp))
        FakeSignInButton(modifier = Modifier.padding(bottom = 0.dp))
        FakeRegisterNow(modifier = Modifier.padding(bottom = 16.dp))
    }
}

@Composable
fun FakeTitleSection(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val titleFontSize = if (screenWidth <= 360.dp) 25.sp else 35.sp
    val subtitleFontSize = if (screenWidth <= 360.dp) 22.sp else 30.sp
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "И снова - здравствуйте!",
            style = TextStyle(
                color = Color.White,
                fontSize = titleFontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Добро подаловать обратно\nмы скучали по вам!",
            style = TextStyle(
                color = Color.White.copy(alpha = 0.8f),
                fontSize = subtitleFontSize,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun FakeSignInSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FakeSignInInput()
        FakeForgotPassword(onClick = {})
    }
}

@Composable
fun FakeSignInInput(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OTFCustom(
            modifier = Modifier.padding(vertical = 16.dp),
            onValueChanged = { },
            placeHolderText = "E-mail"
        )
        OTFCustom(
            modifier = Modifier.padding(bottom = 0.dp),
            onValueChanged = { },
            placeHolderText = "Password",
            keyboardType = KeyboardType.Password,
        )
    }
}

@Composable
fun FakeForgotPassword(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(31.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = "Забыли пароль?",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun FakeSignInButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent // Фон кнопки прозрачный
        ),
        modifier = modifier
            .height(64.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp), // Устанавливаем размер кнопки 64.dp
        contentPadding = PaddingValues(0.dp) // Устанавливаем padding 0, чтобы избежать дополнительного отступа
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            CustomBlue.copy(alpha = 0.75f),
                            CustomBrown.copy(alpha = 0.75f)
                        )
                    ),
                    shape = MaterialTheme.shapes.small
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Text(
                text = "Войти",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun FakeRegisterNow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Нет аккаунта?",
            style = MaterialTheme.typography.body2.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextButton(
            onClick = { },
            contentPadding = PaddingValues(0.dp), // Устанавливаем внешний отступ кнопки в 0.dp
            modifier = Modifier.padding(start = 1.dp) // Устанавливаем отступ между текстами
        ) {
            Text(
                text = "Зарегистрируйся сейчас",
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}
