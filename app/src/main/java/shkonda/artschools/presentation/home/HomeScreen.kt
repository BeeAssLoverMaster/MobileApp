package shkonda.artschools.presentation.home

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.Sunset

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
//    categories

    val activity = LocalContext.current as Activity
//    OnBackPressed(activity = activity)

    HomeScreenContent(modifier, userState)
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    userState: UserState,
//    categoriesState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarSection(modifier = modifier, userState = userState)
        }
    }
}

@Composable
fun TopBarSection(
    modifier: Modifier,
    userState: UserState
) {
    Row(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (userState) {
            is UserState.Nothing -> {}
            is UserState.Loading -> {
                Box(
                    modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    println("кавоЛодинг")
                    CustomLoadingSpinner()
                }
            }

            is UserState.Success -> {
                println("кавоСуксес")
                ProfileImage(
                    modifier = modifier,
                    userProfileImage = userState.data.profilePictureUrl
                )
                UserNameLevel(
                    modifier = modifier,
                    userName = userState.data.userName
                )
                //Notifications
            }

            is UserState.Error -> {
                TopBarError(
                    modifier = modifier,
                    errorMessage = userState.errorMessage
                )
            }
        }
    }
}

@Composable
private fun ProfileImage(
    modifier: Modifier,
    userProfileImage: String
) {
    AsyncImage(
        modifier = modifier
            .size(72.dp)
            .clip(shape = CircleShape)
            .border(
                border = BorderStroke(
                    width = 2.dp, brush = Brush.horizontalGradient(
                        colors = listOf(Sunset, Grapefruit)
                    )
                ),
                shape = CircleShape
            ),
        model = loadImage(context = LocalContext.current, imageUrl = userProfileImage),
        contentDescription = "Изображение профиля",
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun UserNameLevel(modifier: Modifier, userName: String) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello, $userName",
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        Text(
            text = "Lv. 1  Beginner",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun TopBarError(modifier: Modifier, errorMessage: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(48.dp),
                painter = painterResource(id = R.drawable.error_image),
                contentDescription = "Ошибка отображения",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = errorMessage,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}