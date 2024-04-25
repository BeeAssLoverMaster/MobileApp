package shkonda.artschools.core.navigation

object NavRoutes {
    const val home_screen = "home_screen"
    const val profile_screen = "profile_screen"
    const val sign_in_screen = "sign_in_screen"
    const val sign_up_screen = "sign_up_screen"
//    const val edit_profile_screen = "${NavNames.edit_profile_screen}/{${EditProfileScreenArgs.USERNAME}}/{${EditProfileScreenArgs.USER_PROFILE_IMG}}"
    const val edit_profile_screen = "edit_profile_screen"
    const val update_profile_screen = "update_profile_screen"
    const val genres_screen = "genres_screen"
}

object NavNames {
    const val edit_profile_screen = "edit_profile_screen"
}

object BottomNavItems {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile
    )
}

object EditProfileScreenArgs {
    const val USERNAME = "userName"
    const val USER_PROFILE_IMG = "userProfileImage"
}
