package shkonda.artschools.presentation.profile

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.components.CustomSlider
import shkonda.artschools.core.ui.theme.WhiteSmoke
import shkonda.artschools.domain.model.user.UserProfile
import shkonda.artschools.domain.utils.Dimens

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel()) {

    val getUserProfileState by viewModel.getUserProfileState.collectAsState()

    val activity = LocalContext.current as Activity

//    OnBackPressed(activity = activity)

    ProfileScreenContent(
        modifier = modifier,
        getUserProfileState = getUserProfileState,
        viewModel = viewModel
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ProfileScreenContent(
    modifier: Modifier,
    getUserProfileState: GetUserProfileState,
    viewModel: ProfileViewModel
) {
    Scaffold(topBar = { TopAppBar(userData = viewModel.userData, viewModel = viewModel) }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 32.dp + Dimens.AppBarDefaultHeight
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (getUserProfileState) {
                is GetUserProfileState.Loading -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CustomLoadingSpinner()
                    }
                }

                is GetUserProfileState.Success -> {
                    ProfileDetailSection(
                        modifier = modifier,
                        username = getUserProfileState.data.userName,
                        userProfileImg = getUserProfileState.data.profilePictureUrl
                    )
                }

                is GetUserProfileState.Error -> {
                    ErrorSection(
                        modifier = modifier,
                        errorMessage = getUserProfileState.errorMessage
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileDetailSection(
    modifier: Modifier,
    username: String,
    userProfileImg: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage(modifier = modifier, userProfileImg = userProfileImg)
        ProfileInfo(
            modifier = modifier, username = username
        )
    }
}

@Composable
private fun TopAppBar(userData: UserProfile?, viewModel: ProfileViewModel) {
    androidx.compose.material.TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = {
                    viewModel.signIn()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_settings),
                    contentDescription = null,
                    tint = WhiteSmoke
                )
            }
        },
        actions = {},
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
private fun ProfileImage(modifier: Modifier, userProfileImg: String) {
    AsyncImage(
        modifier = modifier
            .size(144.dp)
            .clip(shape = RoundedCornerShape(20))
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primaryVariant
                ), shape = RoundedCornerShape(20)
            ),
        model = loadImage(context = LocalContext.current, imageUrl = userProfileImg),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ProfileInfo(
    modifier: Modifier, username: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserNameAndBiography(
            username = username
        )
    }
}

@Composable
private fun UserNameAndBiography(
    username: String
) {
    Text(
        text = username,
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun ErrorSection(modifier: Modifier, errorMessage: String) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.size(96.dp),
            painter = painterResource(id = R.drawable.error_image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = errorMessage,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center
        )
    }
}
