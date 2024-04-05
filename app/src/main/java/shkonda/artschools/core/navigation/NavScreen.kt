package shkonda.artschools.core.navigation

sealed class NavScreen(val route: String) {
    object SignInScreen : NavScreen(route = NavRoutes.sign_in_screen)
    object SignUpScreen : NavScreen(route = NavRoutes.sign_up_screen)
    object HomeScreen : NavScreen(route = NavRoutes.home_screen)
}