package shkonda.artschools.core.navigation

//import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import shkonda.artschools.core.ui.components.CustomScaffold
import shkonda.artschools.presentation.sign_in.SignInScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = NavScreen.SignInScreen.route
) {
    //Может починить, в случае чего
//    val navController = rememberNavController()
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val destination by Navigator.destination.collectAsState()

    LaunchedEffect(destination) {
        if (destination.isBlank()) {
            navController.navigate(startDestination)
        } else if (navController.currentDestination?.route != destination) {
            navController.navigate(destination)
        }
    }
    CustomScaffold(modifier = modifier.fillMaxSize(), content = {
        NavHost(
            navController = navController,
            startDestination = NavScreen.SignInScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ){
            composable(NavScreen.SignInScreen.route) {
                SignInScreen()
            }
        }
    })

}