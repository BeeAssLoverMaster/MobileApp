package shkonda.artschools.core.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CustomLoadingSpinner() {
    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
}