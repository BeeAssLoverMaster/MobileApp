package shkonda.artschools.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shkonda.artschools.R

@Preview(showBackground = false, name = "Small Phone Preview", widthDp = 320, heightDp = 568)
@Preview(showBackground = true, name = "Medium Phone Preview", widthDp = 360, heightDp = 640)
@Preview(showBackground = true, name = "Large Phone Preview", widthDp = 411, heightDp = 731)
@Preview(showBackground = true, name = "Extra Large Phone Preview", widthDp = 480, heightDp = 800)
@Composable
fun OTFCustomPhonePreviews() {
    OTFCustom(
        modifier = Modifier,
        value = "E-mail",
        onValueChanged = {},
        placeHolderText = "Enter text here"
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTFCustom(
    modifier: Modifier,
    value: String = "",
    onValueChanged: (String) -> Unit,
    placeHolderText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    var showPassword by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp)
                .heightIn(40.dp)
                .border(
                    width = 1.5f.dp, // Устанавливаем толщину границы
                    color = Color.White, // Устанавливаем цвет границы
                    shape = RoundedCornerShape(8.dp)
                ),
            value = value,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = 1,
            singleLine = true,
            keyboardActions = KeyboardActions(onNext = {}),
            placeholder = {
                Text(
                    text = placeHolderText,
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    ),
                )
            },
            visualTransformation = if (keyboardType == KeyboardType.Password && !showPassword) PasswordVisualTransformation(
                mask = '*'
            ) else VisualTransformation.None,
            trailingIcon = {
                if (keyboardType != KeyboardType.Password) null else {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            painter = painterResource(
                                id = if (showPassword) R.drawable.show_password else R.drawable.hide_password
                            ),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            },
            isError = isError,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
        )
    }
}