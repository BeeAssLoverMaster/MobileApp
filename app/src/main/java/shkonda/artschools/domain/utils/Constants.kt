package shkonda.artschools.domain.utils

import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.unit.dp

object Messages {
    const val UNKNOWN = "Что-то пошло не так. Пожалуйста, попробуйте позже"
    const val INTERNET = "Пожалуйста, проверьте ваше интернет соединение"
    const val USER_CRE_SUC = "Пользователь успешно создан"
}

object Dimens {
    val AppBarDefaultHeight = 56.dp
    val ConstantHeight = TextFieldDefaults.MinHeight + 8.dp // Default button, text field height
}