package shkonda.artschools.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
            modifier = modifier.size(
                height = TextFieldDefaults.MinHeight + 8.dp,
                width = TextFieldDefaults.MinWidth
            ),
            value = value,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = 1,
            keyboardActions = KeyboardActions(onNext = {}),
            placeholder = {
                Text(text = placeHolderText)
            },
//             visualTransformation = ,
//            singleLine = ,
//            shape = ,
//            trailingIcon = ,
//            colors = ,
            isError = isError
        )
    }
}