package shkonda.artschools.presentation.main_page.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.OnBackPressed
import shkonda.artschools.core.ui.theme.CustomBlue
import shkonda.artschools.core.ui.theme.CustomBrown
import shkonda.artschools.domain.model.arts.ArtCategory
import shkonda.artschools.domain.model.arts.ArtType
import shkonda.artschools.domain.model.user.UserProfile
import shkonda.artschools.presentation.main_page.home.states.getArtCategoryListState
import shkonda.artschools.presentation.main_page.home.states.getArtCategoryState
import shkonda.artschools.presentation.main_page.home.states.getArtTypeListState
import shkonda.artschools.presentation.main_page.home.states.getUserProfileState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val user = getUserProfileState(viewModel)
    val artCategory = getArtCategoryState(viewModel)
    val artCategoryList = getArtCategoryListState(viewModel)
    val artTypeList = getArtTypeListState(viewModel)

    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(1.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // Полупрозрачный черный цвет
            )
            HomeScreenContent(modifier, user, artCategory, artCategoryList, artTypeList, viewModel)
        }

    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    user: UserProfile?,
    artCategory: ArtCategory?,
    artCategoryList: List<ArtCategory>,
    artTypeList: List<ArtType>,
    viewModel: HomeViewModel
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarSection(
                modifier = modifier,
                user = user,
                artCategory = artCategory,
                artCategoryList = artCategoryList,
                viewModel = viewModel
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        ArtTypesSection(
            modifier = modifier,
            artTypeList = artTypeList,
            onCategoryClick = { typeId ->
                Navigator.navigate(NavScreen.ArtGenresScreen.createArtGenresRoute(typeId))
            }
        )
    }
}

@Composable
fun TopBarSection(
    modifier: Modifier,
    user: UserProfile?,
    artCategory: ArtCategory?,
    artCategoryList: List<ArtCategory>,
    viewModel: HomeViewModel
) {
    Row(
        modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CategoryImage(
            modifier = modifier,
            artCategory = artCategory,
            artCategoryList = artCategoryList,
            viewModel = viewModel
        )
        UserNameLevel(modifier = modifier, username = user?.username ?: "")

        ProfilePoints(modifier = modifier, points = user?.points ?: 0)
    }
}

/*@Composable
private fun ProfileImage(modifier: Modifier, userProfileImage: String) {
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
}*/

@Composable
private fun UserNameLevel(modifier: Modifier, username: String) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val maxWidth = (screenWidth / 2) - 16.dp
    /*Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = username,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            ),
            fontSize = 32.sp
        )
    }*/

    BasicText(
        text = username,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = textSize
        ),
        modifier = Modifier.widthIn(max = maxWidth),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ProfilePoints(modifier: Modifier, points: Int) {
    /*Column() {
        Text(
            text = "$points",
            fontSize = 24.sp
        )
    }*/
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val maxWidth = (screenWidth / 2) - 16.dp
    val iconSize = (screenWidth / 14)
    Row {
        Image(
            painter = painterResource(id = R.drawable.points),
            contentDescription = "",
            modifier = modifier.size(iconSize)
        )
        BasicText(
            text = "$points",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.widthIn(max = maxWidth),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryImage(
    modifier: Modifier,
    artCategory: ArtCategory?,
    artCategoryList: List<ArtCategory>,
    viewModel: HomeViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var showDialog by remember { mutableStateOf(false) }
    val iconSize = (screenWidth / 8) - 16.dp
    val textSize = (screenWidth / 24).value.sp
    val titleSize = (screenWidth / 16).value.sp

    val imageResource = when (artCategory?.id) {
        1L -> R.drawable.visual_art_svg
        2L -> R.drawable.music_art_svg
        else -> R.drawable.no_image
    }

    IconButton(onClick = { showDialog = true }) {

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "art category image",
            modifier = modifier.size(iconSize)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = modifier.padding(16.dp),
            title = { Text(
                text = "Смените искусство!",
                color = Color.LightGray,
                fontSize = titleSize
            ) },
            text = {
                Text(
                    "Выберите что вы хотите изучать сегодня!",
                    color = Color.LightGray,
                    fontSize = textSize
                )
            },
            confirmButton = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    artCategoryList.forEach { artCategory ->
                        ImageTextButton(
                            text = artCategory.categoryName,
                            icon = artCategory.id,
                            onClick = {
                                viewModel.updateUserArtCategory(artCategory.id)
                                showDialog = false
                            }
                        )
                    }
                }
            },
            containerColor = Color.Gray
        )

    }

    /*Column(
        modifier = modifier.padding()
    ) {
        IconButton(onClick = { showDialog = true }) {
            if (artCategory != null) {
                *//*AsyncImage(
                    modifier = modifier
                        .size(72.dp),
                    model = loadImage(
                        context = LocalContext.current,
                        imageUrl = artCategory.imageUrl
                    ),
                    contentDescription = "Изображение категорий",
                    contentScale = ContentScale.Crop
                )*//*
                Image(
                    painter = painterResource(id = R.drawable.visual_art_svg),
                    contentDescription = "",
                    modifier = modifier.size(30.dp)
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Смените искусство!") },
                text = { Text("Выберите что вы хотите изучать сегодня!") },
                buttons = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        artCategoryList.forEach { artCategory ->
                            ImageTextButton(
                                text = artCategory.categoryName,
                                iconUrl = artCategory.imageUrl,
                                onClick = {

                                    showDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            )
        }
    }*/
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

@Composable
fun ArtTypesSection(
    modifier: Modifier,
    artTypeList: List<ArtType>,
    onCategoryClick: (Long) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val cardSize = (screenWidth / 2) - 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Виды искусства",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = modifier.padding(bottom = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(artTypeList) { type ->
                TypeCard(
                    modifier = modifier,
                    typeTitle = type.typeName,
                    typeImageUrl = type.imageName,
                    onCategoryClick = onCategoryClick,
                    item = type,
                    cardSize = cardSize,
                    getId = { it.id }
                )

            }
        }
    }
}

@Composable
fun <T> TypeCard(
    modifier: Modifier,
    typeTitle: String,
    typeImageUrl: String,
    onCategoryClick: (Long) -> Unit,
    item: T,
    cardSize: Dp,
    getId: (T) -> Long
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    Column(
        modifier = modifier.padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .size(
                    height = cardSize,
                    width = cardSize
                )
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                        start = Offset(0f, 0f), // Top-center
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onCategoryClick(getId(item)) },

            shape = RoundedCornerShape(8)
        ) {
            AsyncImage(
                modifier = modifier
                    .size(24.dp),
                model = loadImage(context = LocalContext.current, imageUrl = typeImageUrl),
                contentDescription = "Изображение категорий",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = typeTitle,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            textAlign = TextAlign.Center,
            modifier = modifier.padding(top = 2.dp)
        )
    }
}


@Composable
fun ImageTextButton(text: String, icon: Long, onClick: () -> Unit) {
    val imageResource = when (icon) {
        1L -> R.drawable.visual_art_svg
        2L -> R.drawable.music_art_svg
        else -> R.drawable.no_image
    }
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(64.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val iconSize = (screenWidth / 8) - 16.dp

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            CustomBlue,
                            CustomBrown
                        )
                    ),
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Box(
                modifier = Modifier
                    .width(iconSize)
                    .padding(start = 8.dp)

            ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "",
                    modifier = Modifier.size(iconSize)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White
            )
        }

    }
}

@Composable
private fun CategoriesError(modifier: Modifier, errorMessage: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(96.dp),
                painter = painterResource(id = R.drawable.error_image),
                contentDescription = null
            )
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = errorMessage,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

/*

val artCategories = listOf(
    ArtCategory(1, "Изобразительное искусство", ""),
    ArtCategory(2, "Музыкальное искусство", ""),
)

val artTypes = listOf(
    ArtType(1, "Живопись", "", 1),
    ArtType(2, "Графика", "", 1),
    ArtType(3, "Скульптура", "", 1),
    ArtType(4, "Архитектура", "", 1),
    ArtType(5, "Прикладная живопись", "", 1),
    ArtType(6, "Современное искусство", "", 1),
)

@Preview(name = "Small Phone Preview", widthDp = 320, heightDp = 568)
@Preview(name = "Medium Phone Preview", widthDp = 360, heightDp = 640)
@Preview(name = "Large Phone Preview", widthDp = 411, heightDp = 731)
@Preview(name = "Extra Large Phone Preview", widthDp = 480, heightDp = 800)
@Composable
private fun SignInPrev() {
    FakeHomeScreenContent()
}

@Composable
fun FakeHomeScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FakeTopBarSection(
                modifier = modifier,
                user = UserProfile("very very long username", "", "", 1, 2400),
                artCategory = ArtCategory(1, "Живопись", ""),
                artCategoryList = artCategories
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        FakeArtTypeSection(
            modifier = modifier,
            artTypeList = artTypes,
            onCategoryClick = { typeId ->
                Navigator.navigate(NavScreen.ArtGenresScreen.createArtGenresRoute(typeId))
            }
        )
    }
}

@Composable
fun FakeTopBarSection(
    modifier: Modifier,
    user: UserProfile?,
    artCategory: ArtCategory?,
    artCategoryList: List<ArtCategory>
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FakeCategoryImage(
            modifier = modifier,
            artCategory = artCategory,
            artCategoryList = artCategoryList,
        )
        FakeUserNameLevel(modifier = modifier, username = user?.username ?: "")
        FakeProfilePoints(modifier = modifier, points = user?.points ?: 0)
    }

}

@Composable
fun FakeUserNameLevel(modifier: Modifier, username: String) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
//    val textSize = if (screenWidth < 360.dp) 24.sp else if (screenWidth <= 420.dp) 28.sp else 32.sp
    val textSize = (screenWidth / 16).value.sp
//    val maxWidth = if (screenWidth < 350.dp) 200.dp else if (screenWidth <= 420.dp) 250.dp else 300.dp
    val maxWidth = (screenWidth / 2) - 16.dp
    BasicText(
        text = username,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = textSize
        ),
        modifier = Modifier.widthIn(max = maxWidth),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun FakeCategoryImage(
    modifier: Modifier,
    artCategory: ArtCategory?,
    artCategoryList: List<ArtCategory>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var showDialog by remember { mutableStateOf(false) }
    val iconSize = (screenWidth / 8) - 16.dp
    IconButton(onClick = { showDialog = true }) {
        if (artCategory != null) {
            */
/*AsyncImage(
                modifier = Modifier
                    .size(30.dp),
                model = loadImage(
                    context = LocalContext.current,
                    imageUrl = "http://10.0.2.2:8080/api/image/asset/Visual arts.png",
                    placeHolderAndErrorImage = R.drawable.visual_art_img
                ),
                contentDescription = "Изображение категорий",
                contentScale = ContentScale.Crop
            )*//*

            Image(
                painter = painterResource(id = R.drawable.visual_art_svg),
                contentDescription = "",
                modifier = modifier.size(iconSize)
            )
        }
    }

    if (showDialog) {
        */
/*AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Смените искусство!") },
            text = { Text("Выберите что вы хотите изучать сегодня!") },
            buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    artCategoryList.forEach { artCategory ->
                        ImageTextButton(
                            text = artCategory.categoryName,
                            iconUrl = artCategory.imageUrl,
                            onClick = {
                                showDialog = false
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        )*//*


        val textSize = (screenWidth / 24).value.sp
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = modifier.padding(16.dp),
            title = {Text(text = "Смените искусство!", color = Color.LightGray)},
            text = {Text("Выберите что вы хотите изучать сегодня!", color = Color.LightGray, fontSize = textSize)},
            confirmButton = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    artCategoryList.forEach { artCategory ->
                        ImageTextButton(
                            text = artCategory.categoryName,
                            iconUrl = artCategory.imageUrl,
                            onClick = {
                                showDialog = false
                            }
                        )
                    }
                }
            },
            containerColor = Color.Gray
//            title = { Text(text = "Смените искусство!") },
//            text = { Text("Выберите что вы хотите изучать сегодня!", fontSize = textSize) },
//            buttons = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                ) {
//                    artCategoryList.forEach { artCategory ->
//                        ImageTextButton(
//                            text = artCategory.categoryName,
//                            iconUrl = artCategory.imageUrl,
//                            onClick = {
//                                showDialog = false
//                            }
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
//                }
//            },

        )
    }
}


@Composable
fun FakeProfilePoints(modifier: Modifier, points: Int) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 20).value.sp
    val maxWidth = (screenWidth / 2) - 16.dp
    val iconSize = (screenWidth / 14)
    Row {
        Image(
            painter = painterResource(id = R.drawable.points),
            contentDescription = "",
            modifier = modifier.size(iconSize)
        )
        BasicText(
            text = "$points",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = Modifier.widthIn(max = maxWidth),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Composable
fun FakeArtTypeSection(
    modifier: Modifier,
    artTypeList: List<ArtType>,
    onCategoryClick: (Long) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val cardSize = (screenWidth / 2) - 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Виды искусства",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize
            ),
            modifier = modifier.padding(bottom = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(artTypeList) { type ->
                FakeTypeCard(
                    modifier = modifier,
                    typeTitle = type.typeName,
                    typeImageUrl = type.imageName,
                    onCategoryClick = onCategoryClick,
                    artType = type,
                    cardSize = cardSize
                )
            }
        }
    }
}

@Composable
fun FakeTypeCard(
    modifier: Modifier,
    typeTitle: String,
    typeImageUrl: String,
    onCategoryClick: (Long) -> Unit,
    artType: ArtType,
    cardSize: Dp
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D))
    )
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    Column(
        modifier = modifier.padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .size(
                    height = cardSize,
                    width = cardSize
                )
                .border(
                    width = 4.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                        start = Offset(0f, 0f), // Top-center
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onCategoryClick(artType.id) },

            shape = RoundedCornerShape(8)
        ) {
            AsyncImage(
                modifier = modifier
                    .size(24.dp),
                model = loadImage(context = LocalContext.current, imageUrl = typeImageUrl),
                contentDescription = "Изображение категорий",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = typeTitle,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = textSize,
                fontFamily = FontFamily(Font(R.font.js_math_cmti, FontWeight.Normal))
            ),
            modifier = modifier.padding(top = 2.dp)
        )
    }
}*/
