package shkonda.artschools.core.navigation

object NavRoutes {
    const val home_screen = "home_screen"
    const val sign_in_screen = "login_screen"
    const val sign_up_screen = "register_screen"
    const val profile_screen = "profile_screen"
}

object BottomNavItems {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile
    )
}