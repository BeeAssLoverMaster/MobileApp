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

object NavNames {
    const val confirm_account_screen = "confirm_account_screen"
    const val forgot_password_screen = "forgot_password_screen"
    const val edit_profile_screen = "edit_profile_screen"
    const val update_quiz_screen = "update_quiz_screen"
}