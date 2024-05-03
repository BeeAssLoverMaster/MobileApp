package shkonda.artschools.presentation.profile_page.update_profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import shkonda.artschools.R
import shkonda.artschools.core.common.getRealPathFromURI
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.components.CustomTopBarTitle
import shkonda.artschools.core.ui.components.OutBtnCustom
import shkonda.artschools.domain.utils.Dimens
import shkonda.artschools.domain.utils.Messages
import shkonda.artschools.presentation.profile_page.edit_profile.EditProfileViewModel
import shkonda.artschools.presentation.utils.UpdateProfileBottomSheetSubTitles

private val EDIT_PROFILE_IMG_SIZE = 176.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateProfileViewModel = hiltViewModel()
) {
    val updateProfileState by viewModel.updateProfileState.collectAsState()
    var showUploadImgSection by remember { mutableStateOf(false) }
    var selectedImgUri by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {it != ModalBottomSheetValue.HalfExpanded}
    )
//    val sheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
//    )

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        selectedImgUri = it.toString()
        showUploadImgSection = true
        try {
            val filePath = it?.let { it1 -> getRealPathFromURI(it1, context) }
            if (filePath != null) {
                viewModel.handleMultipartBody(filePath)
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                Messages.UNKNOWN,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    BackHandler(!sheetState.isVisible) {
        Navigator.navigate(NavScreen.EditProfileScreen.route)
    }

    UpdateProfileScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        sheetState = sheetState,
        coroutineScope = coroutineScope,
        updateProfileState = updateProfileState,
        galleryLauncher = galleryLauncher,
        showUploadImgSection = showUploadImgSection,
        onSaveImgClick = {
            viewModel.uploadProfilePicture()
            showUploadImgSection = false
        },
        onCancelImgClick = { showUploadImgSection = false },
        selectedImgUri = selectedImgUri
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UpdateProfileScreenContent(
    modifier: Modifier,
    viewModel: UpdateProfileViewModel,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    updateProfileState: UpdateProfileState,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    showUploadImgSection: Boolean,
    onSaveImgClick: () -> Unit,
    onCancelImgClick: () -> Unit,
    selectedImgUri: String
) {
    if (showUploadImgSection) {
        UploadImageSection(
            modifier = modifier,
            profileImgUrl = selectedImgUri,
            onSaveImgClick = onSaveImgClick,
            onCancelImgClick = onCancelImgClick
        )
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            CustomTopBarTitle(
                modifier = modifier,
                title = "Update Profile"
            )
        }
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EditProfileSection(
                modifier = modifier,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                viewModel = viewModel,
                updateProfileState = updateProfileState,
                galleryLauncher = galleryLauncher
            )
        }
    }
}


@Composable
private fun UploadImageSection(
    modifier: Modifier,
    profileImgUrl: String,
    onSaveImgClick: () -> Unit,
    onCancelImgClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .weight(5f),
            model = loadImage(context = LocalContext.current, imageUrl = profileImgUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                OutBtnCustom(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelImgClick,
                    buttonText = "Cancel"
                )
                OutBtnCustom(
                    modifier = Modifier.weight(1f),
                    onClick = onSaveImgClick,
                    buttonText = "Save"
                )
            }
        }
    }
}


@Composable
private fun UpdateProfileImage(
    modifier: Modifier,
    userImage: String,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        ProfileImage(
            modifier = modifier,
            userImage = userImage
        )
        Box(
            modifier = modifier.size(EDIT_PROFILE_IMG_SIZE),
            contentAlignment = Alignment.BottomEnd
        ) {
            IconButton(
                modifier = modifier
                    .background(color = MaterialTheme.colors.secondary, shape = CircleShape)
                    .clip(CircleShape),
                onClick = { galleryLauncher.launch("image/*") }
            ) {
                Icon(
                    modifier = modifier.padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_photo_camera),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
private fun ProfileImage(modifier: Modifier, userImage: String) {
    AsyncImage(
        modifier = modifier
            .size(EDIT_PROFILE_IMG_SIZE)
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EditProfileSection(
    modifier: Modifier,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    viewModel: UpdateProfileViewModel,
    updateProfileState: UpdateProfileState,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    when (updateProfileState) {
        is UpdateProfileState.Nothing -> {
//            ModalBottomSheetLayout(
//                modifier = modifier.fillMaxSize(),
//                sheetContent = {
//                    SheetContent(
//                        modifier = modifier,
//                        viewModel = viewModel,
//                        coroutineScope = coroutineScope,
//                        sheetState = sheetState
//                    )
//                },
//                sheetState = sheetState,
//                sheetBackgroundColor = MaterialTheme.colors.background
//            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = Dimens.AppBarDefaultHeight + 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UpdateProfileImage(
                        modifier = modifier,
                        userImage = viewModel.profilePictureUrl,
                        galleryLauncher = galleryLauncher
                    )
                    Spacer(modifier = modifier.padding(vertical = 16.dp))

                    Divider(
                        modifier = modifier.fillMaxWidth(),
                        thickness = 1.dp,
                    )

                }
//            }
        }
        is UpdateProfileState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CustomLoadingSpinner()
            }
        }
        is UpdateProfileState.Success -> {
            Toast.makeText(
                LocalContext.current,
                "Your profile successfully updated.",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetUpdateProfileState()
        }
        is UpdateProfileState.Error -> {
            Toast.makeText(
                LocalContext.current,
                updateProfileState.errorMessage,
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetUpdateProfileState()
        }
    }
}
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//private fun SheetContent(
//    modifier: Modifier,
//    viewModel: UpdateProfileViewModel,
//    coroutineScope: CoroutineScope,
//    sheetState: ModalBottomSheetState
//) {
//    if (viewModel.isBiographySelected) {
//        BiographyBottomSheet(
//            modifier = modifier,
//            value = viewModel.biographyBottomSheet,
//            onValueChanged = { viewModel.updateBiographyField(it) },
//            onSaveClick = {
//                viewModel.updateProfile()
//                coroutineScope.launch {
//                    sheetState.hide()
//                }
//            },
//            onCancelClick = {
//                coroutineScope.launch {
//                    sheetState.hide()
//                }
//            }
//        )
//    } else {
//        RealNameBottomSheet(
//            modifier = modifier,
//            onFirstNameChanged = { viewModel.updateFirstNameField(it) },
//            onLastNameChanged = { viewModel.updateLastNameField(it) },
//            firstNameValue = viewModel.firstNameBottomSheet,
//            lastNameValue = viewModel.lastNameBottomSheet,
//            onSaveClick = {
//                viewModel.updateProfile()
//                coroutineScope.launch {
//                    sheetState.hide()
//                }
//            },
//            onCancelClick = {
//                coroutineScope.launch {
//                    sheetState.hide()
//                }
//            }
//        )
//    }
//}