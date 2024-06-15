package shkonda.artschools.presentation.auth_page.sign_up

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
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
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
import shkonda.artschools.core.ui.components.OutBtnCustom
import shkonda.artschools.core.ui.theme.CustomBlue
import shkonda.artschools.core.ui.theme.CustomBrown
import shkonda.artschools.presentation.utils.Messages

// Функция для создания экрана регистрации
@Composable
fun SignUpScreen(modifier: Modifier = Modifier, viewModel: SignUpViewModel = hiltViewModel()
) {
    // Получение состояния создания пользователя из ViewModel
    val createUserState by viewModel.createUserState.collectAsState()
    val registerInputFieldState by viewModel.registerInputFieldState.collectAsState()
    // Отображение контента экрана регистрации
    RegisterScreenContent(modifier = modifier, viewModel = viewModel, registerInputFieldState = registerInputFieldState, createUserState = createUserState)
}
// Функция для отображения контента экрана регистрации
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreenContent(modifier: Modifier, viewModel: SignUpViewModel, registerInputFieldState: RegisterInputFieldState, createUserState: CreateUserState) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.background), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))
            SignUp(modifier = modifier, viewModel = viewModel, registerInputFieldState = registerInputFieldState, createUserState = createUserState)
        }
    }
}
// Функция для отображения формы регистрации
@Composable
fun SignUp(modifier: Modifier, viewModel: SignUpViewModel, registerInputFieldState: RegisterInputFieldState, createUserState: CreateUserState) {
    when (createUserState) {
        is CreateUserState.Nothing -> {
            Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                TitleSection(modifier = modifier)
                InputSection(modifier = modifier, viewModel = viewModel)
                RegisterButton(modifier = modifier, viewModel = viewModel)
                SignInNow(modifier = modifier, onNavigate = {viewModel.navigateSignInScreen()})
            }
        }
        is CreateUserState.Loading -> { Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { CustomLoadingSpinner() } }
        is CreateUserState.Success -> { ShowMessage(message = Messages.USER_CREATE_SUCCESS) }
        is CreateUserState.Error -> { ShowMessage(message = createUserState.errorMessage, createUserState = createUserState, onReset = { viewModel.resetCreateUserState() }) }
    }
}
// Функция для отображения заголовка на экране регистрации
@Composable
fun TitleSection(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val titleFontSize = if (screenWidth <= 360.dp) 25.sp else 30.sp
    val subtitleFontSize = if (screenWidth <= 360.dp) 22.sp else 30.sp
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "ПРИСОЕДИНЯЙСЯ К НАМ", style = TextStyle(color = Color.White, fontSize = titleFontSize, fontWeight = FontWeight.Bold))
    }
}
// Функция для отображения полей ввода (имя пользователя, Email, пароль) на экране регистрации
@Composable
fun InputSection(modifier: Modifier, viewModel: SignUpViewModel
) {
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // Поле для ввода имени пользователя
        OTFCustom(modifier = Modifier.padding(bottom = 16.dp, top = 32.dp), value = viewModel.username, onValueChanged = { viewModel.updateUserNameField(newValue = it) }, placeHolderText = "Имя пользователя", isError = viewModel.userNameError)
        // Поле для ввода Email
        OTFCustom(modifier = Modifier.padding(bottom = 16.dp), value = viewModel.userEmail, keyboardType = KeyboardType.Email, onValueChanged = { viewModel.updateUserEmailField(newValue = it) }, placeHolderText = "E-mail", isError = viewModel.userEmailError)
        // Поле для ввода пароля
        OTFCustom(modifier = Modifier.padding(bottom = 16.dp), value = viewModel.userPassword, onValueChanged = { viewModel.updateUserPasswordField(newValue = it) }, placeHolderText = "Пароль", keyboardType = KeyboardType.Password, isError = viewModel.userPasswordError)
    }
}
// Функция для отображения кнопки регистрации
@Composable
fun RegisterButton(modifier: Modifier, viewModel: SignUpViewModel
) {
    Button({ viewModel.createUser() }, modifier.height(64.dp).padding(vertical = 8.dp, horizontal = 16.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), contentPadding = PaddingValues(0.dp) // Устанавливаем padding 0, чтобы избежать дополнительного отступа
    ) {
        Box(modifier = Modifier.fillMaxSize().background(brush = Brush.horizontalGradient(colors = listOf(CustomBlue.copy(alpha = 0.75f), CustomBrown.copy(alpha = 0.75f))), shape = MaterialTheme.shapes.small).border(width = 1.dp, color = Color.White, shape = MaterialTheme.shapes.small)
        ) {
            Text(text = "Присоединиться", color = Color.White, modifier = Modifier.align(Alignment.Center))
        }
    }
}
// Функция для отображения ссылки на вход в уже существующий аккаунт
@Composable
fun SignInNow(modifier: Modifier, onNavigate: () -> Unit) {
    Row(modifier = modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Уже есть аккаунт?", style = typography.body2.copy(color = Color.White))
        Spacer(modifier = Modifier.padding(4.dp))
        TextButton(onClick = onNavigate, contentPadding = PaddingValues(0.dp)) {
            Text(text = "Войдите", style = typography.body2.copy(fontWeight = FontWeight.Bold, color = Color.White))
        }
    }
}
@Composable
private fun ShowMessage(message: String, createUserState: CreateUserState = CreateUserState.Nothing, onReset: () -> Unit = {}) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
    if (createUserState is CreateUserState.Error) {
        onReset() // Сброс состояния создания пользователя, если возникла ошибка
    }
}

@Preview(showBackground = true, name = "Small Phone Preview", widthDp = 320, heightDp = 568)
@Preview(showBackground = true, name = "Medium Phone Preview", widthDp = 360, heightDp = 640)
@Preview(showBackground = true, name = "Large Phone Preview", widthDp = 411, heightDp = 731)
@Preview(showBackground = true, name = "Extra Large Phone Preview", widthDp = 480, heightDp = 800)
@Composable
private fun SignUpPrev() {
    SignUpFakeContent()
}

@Composable
fun SignUpFakeContent(modifier: Modifier = Modifier) {
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
            FakeSignUp()
        }
    }
}

@Composable
fun FakeSignUp(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        FakeTitleSection(modifier = Modifier.padding(bottom = 0.dp))
        FakeInputSection(modifier = Modifier.padding(bottom = 0.dp))
        FakeRegisterButton(modifier = Modifier.padding(bottom = 0.dp))
        FakeSignInNow()
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
            text = "ПРИСОЕДИНЯЙСЯ К НАМ",
            style = TextStyle(
                color = Color.White,
                fontSize = titleFontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun FakeInputSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OTFCustom(
            modifier = Modifier.padding(bottom = 16.dp, top = 32.dp),
            onValueChanged = { },
            placeHolderText = "Имя пользователя"
        )
        OTFCustom(
            modifier = Modifier.padding(bottom = 16.dp),
            onValueChanged = { },
            placeHolderText = "E-mail",
            keyboardType = KeyboardType.Email,
        )
        OTFCustom(
            modifier = Modifier.padding(bottom = 16.dp),
            onValueChanged = { },
            placeHolderText = "Пароль",
            keyboardType = KeyboardType.Password,
        )
        OTFCustom(
            modifier = Modifier.padding(bottom = 16.dp),
            onValueChanged = { },
            placeHolderText = "Пароль ещё раз",
            keyboardType = KeyboardType.Password,
        )
    }
}

@Composable
fun FakeRegisterButton(modifier: Modifier = Modifier) {
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
                text = "Присоединиться",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun FakeSignInNow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Уже есть аккаунт?",
            style = typography.body2.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextButton(
            onClick = { },
            contentPadding = PaddingValues(0.dp), // Устанавливаем внешний отступ кнопки в 0.dp
            modifier = Modifier.padding(start = 1.dp) // Устанавливаем отступ между текстами
        ) {
            Text(
                text = "Войдите",
                style = typography.body2.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}