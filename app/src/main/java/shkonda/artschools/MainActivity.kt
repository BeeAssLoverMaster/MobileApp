package shkonda.artschools

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import shkonda.artschools.core.common.getToken
import shkonda.artschools.core.navigation.NavGraph
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.ui.theme.ArtSchoolsTheme
import shkonda.artschools.presentation.sign_in.SignInScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArtSchoolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    if (sharedPreferences.getToken().isNullOrEmpty()) {
//                        NavGraph()
//                    } else {
//                        NavGraph(startDestination = NavScreen.HomeScreen.route,)
//                    }
                    NavGraph()
//                    NavGraph(startDestination = NavScreen.SignInScreen.route)
                }
            }
        }
    }
}
