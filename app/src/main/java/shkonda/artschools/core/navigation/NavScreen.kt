package shkonda.artschools.core.navigation

sealed class NavScreen(val route: String) {
    object SignInScreen : NavScreen(route = NavRoutes.sign_in_screen)
    object SignUpScreen : NavScreen(route = NavRoutes.sign_up_screen)
    object HomeScreen : NavScreen(route = NavRoutes.home_screen)
    object ProfileScreen : NavScreen(route = NavRoutes.profile_screen)
    object EditProfileScreen : NavScreen(route = NavRoutes.edit_profile_screen)
    object UpdateProfileScreen : NavScreen(route = NavRoutes.update_profile_screen)
//    object GenresScreen : NavScreen(route = NavRoutes.update_profile_screen)
    object GenresScreen : NavScreen(route = "genres_screen/{categoryId}") {
        fun createRoute(categoryId: String) = "genres_screen/$categoryId"
    }
}