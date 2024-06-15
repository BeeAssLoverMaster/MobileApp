package shkonda.artschools.core.ui.components

import android.graphics.Color
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun OutBtnCustom(
    modifier: Modifier,
    onClick: () -> Unit,
    buttonText: String,
    enabled: Boolean = true,
) {
    OutlinedButton(
        modifier = if (enabled) modifier
            .size(
                height = TextFieldDefaults.MinHeight + 8.dp,
                width = TextFieldDefaults.MinWidth
            )
            .bounceClick()
        else
            modifier
                .size(
                    height = TextFieldDefaults.MinHeight + 8.dp,
                    width = TextFieldDefaults.MinWidth
                )
                .alpha(0.5f),
        onClick = onClick,
        shape = RoundedCornerShape(20),
        enabled = enabled
    ) {
        Text(text = buttonText)
    }
}

enum class BtnState {Pressed, Idle}

private fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(BtnState.Idle) }
    val scale by animateFloatAsState(if (buttonState == BtnState.Pressed) 0.70f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == BtnState.Pressed) {
                    waitForUpOrCancellation()
                    BtnState.Idle
                } else {
                    awaitFirstDown(false)
                    BtnState.Pressed
                }
            }
        }
}