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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.encodeForSafe
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavNames
import shkonda.artschools.core.navigation.Navigator
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
    Scaffold(topBar = { TopAppBar(userData = viewModel.userData) }) {
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
                        userName = getUserProfileState.data.userName,
                        biography = getUserProfileState.data.biography ?: "",
                        score = getUserProfileState.data.score,
                        userProfileImg = getUserProfileState.data.profilePictureUrl
                    )
                    /*AchievementsSection(modifier = modifier)
                    InventorySection(modifier = modifier)*/
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
    userName: String,
    biography: String,
    score: Int,
    userProfileImg: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage(modifier = modifier, userProfileImg = userProfileImg)
        ProfileInfo(modifier = modifier, userName = userName, biography = biography, score = score)
    }
}

@Composable
private fun TopAppBar(userData: UserProfile?) {
    androidx.compose.material.TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = {
                    if (userData != null) {
                        Navigator.navigate(
                            "${NavNames.edit_profile_screen}/${userData.firstName}/${userData.lastName}/${userData.userName}/${
                                encodeForSafe(
                                    userData.profilePictureUrl
                                )
                            }"
                        ) {}
                    }
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
private fun ProfileInfo(modifier: Modifier, userName: String, biography: String, score: Int) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserNameAndBiography(userName = userName, biography = biography)
        UserLevelAndStatistics(modifier = modifier, score = score)
    }
}

@Composable
private fun UserNameAndBiography(userName: String, biography: String) {
    Text(
        text = userName,
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
    Text(
        text = biography,
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun UserLevelAndStatistics(modifier: Modifier, score: Int) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(shape = RoundedCornerShape(20))
    ) {
        Column {
            LevelSlider(modifier = modifier)
        }
    }
}

@Composable
private fun LevelSlider(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "4000 / 10000 XP",
            style = MaterialTheme.typography.h6.copy(color = Color.Gray),
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.primaryVariant
        )
        CustomSlider(
            modifier = modifier.fillMaxWidth(),
            trackHeight = 12.dp,
            onValueChange = { value -> },
            value = 40f
        )
    }
}
//
//@Composable
//private fun UserStatistics(modifier: Modifier, score: Int) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
//        horizontalArrangement = Arrangement.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Statistics(
//            modifier = modifier.padding(start = 4.dp),
//            iconId = R.drawable.ic_baseline_arrow_circle_up,
//            value = score,
//            description = "Score"
//        )
//        Statistics(
//            modifier = modifier.padding(start = 4.dp),
//            iconId = R.drawable.ic_baseline_check_circle,
//            value = 218,
//            description = "Correct Answers"
//        )
//    }
//}

@Composable
private fun Statistics(modifier: Modifier, iconId: Int, value: Int, description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedButton(
            modifier = modifier.size(48.dp),
            onClick = {},
            enabled = false,
            elevation = ButtonDefaults.elevation(disabledElevation = 8.dp),
            border = BorderStroke(
                width = 0.dp,
                color = Color.Transparent
            ),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(25),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.Black
            )
        }
        Column(
            modifier = modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$value",
                style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primaryVariant
            )
            Text(
                text = description, style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
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
