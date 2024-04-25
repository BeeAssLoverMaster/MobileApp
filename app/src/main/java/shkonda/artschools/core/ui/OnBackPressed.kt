package shkonda.artschools.core.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import shkonda.artschools.core.navigation.Navigator

@Composable
fun OnBackPressed(activity: Activity) {
    BackHandler {
        activity.finish()
    }
}

@Composable
fun OnBackPressed(targetRoute: String) {
    BackHandler() {
        Navigator.navigate(destination = targetRoute) {}
    }
}