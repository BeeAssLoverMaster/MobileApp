package shkonda.artschools.presentation.profile

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import shkonda.artschools.DataStoreManager
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavNames
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.OnBackPressed
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.theme.WhiteSmoke
import shkonda.artschools.domain.model.user.UserProfile

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val dataStoreManager = DataStoreManager(LocalContext.current)
    val getUserProfileState by viewModel.getUserProfileState.collectAsState()


    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    ProfileScreenContent(
        modifier = modifier,
        getUserProfileState = getUserProfileState,
        dataStoreManager = dataStoreManager,
        viewModel = viewModel
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreenContent(
    modifier: Modifier,
    getUserProfileState: GetUserProfileState,
    dataStoreManager: DataStoreManager,
    viewModel: ProfileViewModel
) {
    Scaffold(topBar = { TopAppBar(userData = viewModel.userData, dataStoreManager) }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 32.dp
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
private fun TopAppBar(userData: UserProfile?, dataStoreManager: DataStoreManager) {
    val coroutine = rememberCoroutineScope()
    androidx.compose.material.TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = {

                //Возможно проверка была в ошибке, проверь что вообще содержится в userData, Возможно она пуста
                if (userData != null) {
                    println("userData: $userData \nuserData.userName: ${userData.userName}\nuserData.profilePictureUrl: ${userData.profilePictureUrl}")
                    println("${NavNames.edit_profile_screen}/${userData.userName}/${userData.profilePictureUrl}")
                    println(NavScreen.EditProfileScreen.route)
//                    Navigator.navigate(
////                        "${NavNames.edit_profile_screen}/${userData.userName}/${userData.profilePictureUrl}"
//                        "$NavNames.edit_profile_screen"
//                    ) {}
                    coroutine.launch {
                        dataStoreManager.saveUserData(userData)
                    }

                    Navigator.navigate(
//                        "${NavNames.edit_profile_screen}/${userData.userName}/${userData.profilePictureUrl}"
                        NavScreen.EditProfileScreen.route
                    ) {}
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_settings),
                    contentDescription = "настройки",
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
fun ProfileDetailSection(
    modifier: Modifier,
    username: String,
    userProfileImg: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage(modifier = modifier, userProfileImg = userProfileImg)
        ProfileInfo(modifier = modifier, username = username)
    }
}

@Composable
fun ProfileImage(
    modifier: Modifier,
    userProfileImg: String
) {
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
        contentDescription = "Изображение профиля",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProfileInfo(modifier: Modifier, username: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Username(username = username)
    }
}

@Composable
fun Username(username: String) {
    Text(
        text = username,
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun ErrorSection(modifier: Modifier, errorMessage: String) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.size(96.dp),
            painter = painterResource(id = R.drawable.error_image),
            contentDescription = "Ошибка",
            contentScale = ContentScale.Crop
        )
        Text(
            text = errorMessage,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center
        )
    }
}