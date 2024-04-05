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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import shkonda.artschools.core.ui.components.CustomScaffold
import shkonda.artschools.core.ui.theme.Black
import shkonda.artschools.core.ui.theme.TransparentWhite
import shkonda.artschools.presentation.home.HomeScreen
import shkonda.artschools.presentation.sign_in.SignInScreen
import shkonda.artschools.presentation.sign_up.SignUpScreen


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
            composable(NavScreen.SignUpScreen.route) {
                SignUpScreen()
            }
            composable(NavScreen.HomeScreen.route) {
                HomeScreen()
            }
        }

        BottomAppBar(
            modifier = modifier,
            currentRoute = currentRoute,
            navController = navController,
            onClick = {},
        )
    })

}

@Composable
private fun BottomAppBar(
    modifier: Modifier,
    currentRoute: String?,
    navController: NavController,
    onClick: () -> Unit,
//    showFab: Boolean
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
        if (index != 2) {
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
}