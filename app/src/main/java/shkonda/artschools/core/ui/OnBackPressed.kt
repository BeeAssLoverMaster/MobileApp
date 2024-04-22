package shkonda.artschools.core.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun OnBackPressed(activity: Activity) {
    BackHandler {
        activity.finish()
    }
}