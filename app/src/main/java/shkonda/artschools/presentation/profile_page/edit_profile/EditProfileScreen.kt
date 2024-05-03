package shkonda.artschools.presentation.profile_page.edit_profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.DataStoreManager
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.components.CustomTopBarTitle
import shkonda.artschools.presentation.utils.EditProfileScreenPreferencesNames

private val SETTINGS_PROFILE_IMG_SIZE = 108.dp
private val PREFERENCES_HEIGHT = 56.dp

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val dataStoreManager = DataStoreManager(LocalContext.current)

    var username by remember {
        mutableStateOf("")
    }
    var userImg by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        dataStoreManager.getUserData().collect { data ->
            username = data.username
            userImg = data.profileImage
        }
    }

    BackHandler {
        Navigator.navigate(NavScreen.ProfileScreen.route)
    }

    EditProfileScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        username = username,
        userImage = userImg
    )
}

@Composable
fun EditProfileScreenContent(
    modifier: Modifier,
    viewModel: EditProfileViewModel,
    username: String,
    userImage: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        CustomTopBarTitle(modifier = modifier, title = "Edit Profile")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileSection(
            modifier = modifier,
//            username = viewModel.username,
//            userImage = viewModel.profilePicture,
            username = username,
            userImage = userImage,
            onEditProfileClick = {
                Navigator.navigate(NavScreen.UpdateProfileScreen.route)
            },
            onPreferenceClick = {
                viewModel.setPreferenceOnClick(it)
            }
        )
    }
}

@Composable
fun ProfileSection(
    modifier: Modifier,
    username: String,
    userImage: String,
    onEditProfileClick: () -> Unit,
    onPreferenceClick: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileImage(
                modifier = modifier,
                userImage = userImage
            )
            Username(
                modifier = modifier,
                username = username
            )
            EditProfileButton(
                modifier = modifier,
                onEditProfileClick = onEditProfileClick
            )
        }

        Preferences(
            modifier = modifier.weight(1f),
            onPreferenceClick = onPreferenceClick
        )
    }
}

@Composable
fun ProfileImage(modifier: Modifier, userImage: String) {
    AsyncImage(
        modifier = modifier
            .size(SETTINGS_PROFILE_IMG_SIZE)
            .clip(CircleShape)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primaryVariant
                ), shape = CircleShape
            ),
        model = loadImage(context = LocalContext.current, imageUrl = userImage),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Username(
    modifier: Modifier,
    username: String
) {
    Column(
        modifier = modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "@$username",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun EditProfileButton(modifier: Modifier, onEditProfileClick: () -> Unit) {
    TextButton(
        modifier = modifier.padding(top = 16.dp),
        onClick = onEditProfileClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Blue)
    ) {
        Text(
            modifier = modifier.padding(horizontal = 8.dp),
            text = "Edit Profile >",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
fun Preferences(
    modifier: Modifier,
    onPreferenceClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
            )
            .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        preferenceList.forEach {
            Preference(
                iconId = it.iconId,
                text = it.text,
                onPreferenceClick = { onPreferenceClick(it.text) },
                action = it.action
            )
        }
    }
}

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String,
    onPreferenceClick: () -> Unit,
    action: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(PREFERENCES_HEIGHT)
            .clickable(onClick = onPreferenceClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = iconId), contentDescription = null)
            Text(
                modifier = modifier.padding(start = 16.dp),
                text = text,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        action()
    }
}

data class PreferenceModel(
    val iconId: Int,
    val text: String,
    val onPreferenceClick: () -> Unit,
    val action: @Composable RowScope.() -> Unit
)

private val preferenceList = listOf(
    PreferenceModel(
        iconId = R.drawable.ic_baseline_language,
        text = EditProfileScreenPreferencesNames.LANGUAGE,
        onPreferenceClick = {}
    ) {
        Text(
            text = "English >",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_outline_dark_mode,
        text = EditProfileScreenPreferencesNames.DARK_MODE,
        onPreferenceClick = {}
    ) {
        Switch(checked = true, onCheckedChange = {})
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_logout,
        text = EditProfileScreenPreferencesNames.LOG_OUT,
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
)