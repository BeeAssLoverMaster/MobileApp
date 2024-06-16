package shkonda.artschools.core.navigation

//import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import shkonda.artschools.R
import shkonda.artschools.core.ui.components.CustomScaffold
import shkonda.artschools.presentation.auth_page.sign_in.SignInScreen
import shkonda.artschools.presentation.auth_page.sign_up.SignUpScreen
import shkonda.artschools.presentation.main_page.art_genres.ArtGenresScreen
import shkonda.artschools.presentation.main_page.article.ArticleScreen
import shkonda.artschools.presentation.main_page.article.BioArticleScreen
import shkonda.artschools.presentation.main_page.home.HomeScreen
import shkonda.artschools.presentation.main_page.questions.QuestionScreen
import shkonda.artschools.presentation.main_page.quizzes.QuizzesScreen
import shkonda.artschools.presentation.profile_page.edit_profile.EditProfileScreen
import shkonda.artschools.presentation.profile_page.profile.ProfileScreen
import shkonda.artschools.presentation.profile_page.update_profile.UpdateProfileScreen
import shkonda.artschools.presentation.school_page.ArtSchool
import shkonda.artschools.presentation.school_page.ArtSchoolScreen
import shkonda.artschools.presentation.school_page.ArtSchoolType


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = NavScreen.SignInScreen.route
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val destination by Navigator.destination.collectAsState()

    var tempType by remember { mutableStateOf(-1L) }
    var tempGenre by remember { mutableStateOf(-1L) }
    var tempQuiz by remember { mutableStateOf(-1L) }
    var tempArticle by remember { mutableStateOf(-1L) }

    LaunchedEffect(destination) {
        if (destination.isBlank()) {
            navController.navigate(startDestination)
        } else if (navController.currentDestination?.route != destination) {
            navController.navigate(destination)
        }
    }
    CustomScaffold(modifier = modifier.fillMaxSize(), content = {
        NavHost(
            modifier = modifier.padding(it),
            navController = navController,
            startDestination = startDestination,
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
        ) {
            composable(NavScreen.SignInScreen.route) {
                SignInScreen()
            }
            composable(NavScreen.SignUpScreen.route) {
                SignUpScreen()
            }
            composable(NavScreen.HomeScreen.route) {
                HomeScreen()
            }
            composable(NavScreen.ArtSchoolScreen.route) {
                ArtSchoolScreen(
                    /*artSchools = listOf(
                        ArtSchool(
                            id = 0L,
                            name = "Художественная школа имени Цивелёва Г.А.",
                            description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
                            location = "Комсомольск-на-Амуре, ул. Пушкина, дом Колотушкина",
                            imageUrl = R.drawable.first_school,
                            type = ArtSchoolType.Music,
                            programs = listOf("violin", "piano", "guitar")
                        ),
                        ArtSchool(
                            id = 1L,
                            name = "Детская музыкальная школа",
                            description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
                            location = "Комсомольск-на-Амуре, Проспект Первостроителей, 41",
                            imageUrl = R.drawable.second_school,
                            type = ArtSchoolType.Music,
                            programs = listOf("violin", "piano", "guitar")
                        ),
                        ArtSchool(
                            id = 2L,
                            name = "Детская школа искусств №6",
                            description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
                            location = "г. Хабаровск, ул. Ленинградская,30",
                            imageUrl = R.drawable.third_school,
                            type = ArtSchoolType.Music,
                            programs = listOf("Фортепиано", "Народные инструменты", "Вокал")
                        ),
                        ArtSchool(
                            id = 3L,
                            name = "Детская школа искусств №7",
                            description = "Прекрасная школа, которая обучает искусству по методике Дани Трэгхо, великого искусствоведа",
                            location = "г. Хабаровск, Переулок Пилотов, 1",
                            imageUrl = R.drawable.fourth_school,
                            type = ArtSchoolType.Music,
                            programs = listOf("violin", "piano", "guitar")
                        )
                    ),*/
                )
            }
            composable(NavScreen.ProfileScreen.route) {
                ProfileScreen()
            }
            composable(route = NavScreen.UpdateProfileScreen.route) {
                UpdateProfileScreen()
            }
            composable(
                route = NavScreen.EditProfileScreen.route,
            ) {
                EditProfileScreen()
                println("Info from composable: ${EditProfileScreenArgs.USERNAME}, ${EditProfileScreenArgs.USER_PROFILE_IMG}")
            }
            composable(
                route = NavScreen.ArtGenresScreen.route,
                arguments = listOf(
                    navArgument("typeId") { type = NavType.LongType }
                )
            ) {
                val typeId = it.arguments?.getLong("typeId")
                if (typeId != null) {
                    tempType = typeId
                }
                println("categoryID в NavGraph: $typeId")
                typeId?.let {
                    ArtGenresScreen(typeId = it)
                }
            }
            composable(
                route = NavScreen.QuizzesScreen.route,
                arguments = listOf(
                    navArgument("genreId") { type = NavType.LongType }
                )
            ) {
                val genreId = it.arguments?.getLong("genreId")
                if (genreId != null) {
                    tempGenre = genreId
                }
                genreId?.let {
                    QuizzesScreen(genreId = it, typeId = tempType)
                }

            }
            composable(
                route = NavScreen.ArticleScreen.route,
                arguments = listOf(
                    navArgument("quizId") { type = NavType.LongType }
                )
            ) {
                val quizId = it.arguments?.getLong("quizId")
                if (quizId != null) {
                    tempQuiz = quizId
                }
                quizId?.let {
                    ArticleScreen(quizId = it, genreId = tempGenre)
                }
            }
            composable(
                route = NavScreen.BioArticleScreen.route,
                arguments = listOf(
                    navArgument("artistId") { type = NavType.LongType }
                )
            ) {
                val artistId = it.arguments?.getLong("artistId")
                artistId?.let {
                    BioArticleScreen(artistId = it, quizId = tempQuiz)
                }
            }
            composable(
                route = NavScreen.QuestionScreen.route,
                arguments = listOf(
                    navArgument("quizId") { type = NavType.LongType }
                )
            ) {
                val quizId = it.arguments?.getLong("quizId")
                quizId?.let {
                    QuestionScreen(quizId = quizId)
                }
            }
        }
        BottomAppBar(
            modifier = modifier,
            currentRoute = currentRoute,
            navController = navController,
        )
    })
}

/*
@Composable
private fun BottomAppBar(
    modifier: Modifier,
    currentRoute: String?,
    navController: NavController
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        BottomNavItems.items.forEach { navItem ->
            if (navItem.route == currentRoute) {
                androidx.compose.material.BottomAppBar(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .navigationBarsPadding()
                        .clip(shape = RoundedCornerShape(20))
                        .border(
                            border = BorderStroke(width = 1.dp, color = Black),
                            shape = RoundedCornerShape(20)
                        ),
                    backgroundColor = TransparentWhite,
                    elevation = 0.dp
                ) {
                    BottomAppBarContent(
                        currentRoute = currentRoute,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomAppBarContent(currentRoute: String?, navController: NavController) {
    BottomNavItems.items.forEachIndexed { index, screen ->
        if (index != 3) {
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                selectedContentColor = Black,
                unselectedContentColor = Color.Gray,
                onClick = {
                    if (currentRoute == screen.route) {
                        return@BottomNavigationItem
                    }

                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            NavScreen.HomeScreen.route.let {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                    )
                }
            )
        } else {
            BottomNavigationItem(
                selected = false,
                onClick = {},
                enabled = false,
                label = {},
                icon = {}
            )
        }
    }
}*/

@Composable
private fun BottomAppBar(
    modifier: Modifier,
    currentRoute: String?,
    navController: NavController
) {
    // Use a Box to align the content at the bottom center
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        BottomNavItems.items.forEach { bottomNavItem ->
            if (bottomNavItem.route == currentRoute) {
                androidx.compose.material.BottomAppBar(
                    modifier = modifier
                        .padding(horizontal = 16.dp)
//                .navigationBarsPadding()
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 0.dp
                            )
                        ) // Rounded corners for the bar
                        .border(
                            border = BorderStroke(
                                width = 2.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF7885F3).copy(alpha = 0.6f),
                                        Color.Transparent
                                    ),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            ),
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 0.dp
                            )
                        ),
                    backgroundColor = Color(0xFF1E2245)/*.copy(alpha = 0.1f)*/, // Custom background color
                    elevation = 0.dp
                ) {
                    BottomAppBarContent(
                        currentRoute = currentRoute,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomAppBarContent(currentRoute: String?, navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val imageSize = (screenWidth / 6) - 16.dp

    BottomNavItems.items.forEachIndexed { index, screen ->
        // Filter to show only valid icons
        if (index < 3) {
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                selectedContentColor = Color(0xFFB9B5FF), // Selected icon color
                unselectedContentColor = Color(0xFFB9B5FF).copy(alpha = 0.6f), // Unselected icon color
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            navController.graph.findStartDestination().id.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        modifier = Modifier.size(imageSize),
                        contentDescription = null,
                        tint = Color(0xFFB9B5FF)
                    )
                    /*Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFFB9B5FF), CircleShape)
                    ) {

                    }*/
                }
            )
        } else {
            BottomNavigationItem(
                selected = false,
                onClick = {},
                enabled = false,
                icon = {}
            )
        }
    }
}
