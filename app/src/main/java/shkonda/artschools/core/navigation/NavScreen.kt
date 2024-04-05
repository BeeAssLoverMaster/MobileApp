package shkonda.artschools.core.navigation

sealed class NavScreen(val route: String) {
    object SignInScreen : NavScreen(route = NavRoutes.sign_in_screen)
    object RegisterScreen : NavScreen(route = NavRoutes.register_screen)
    object HomeScreen : NavScreen(route = NavRoutes.home_screen)
}