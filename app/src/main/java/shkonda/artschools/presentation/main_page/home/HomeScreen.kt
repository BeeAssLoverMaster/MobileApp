package shkonda.artschools.presentation.main_page.home

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.OnBackPressed
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.Sunset
import shkonda.artschools.domain.model.arts.ArtCategory
import shkonda.artschools.domain.model.arts.ArtType
import shkonda.artschools.domain.model.user.UserProfile
import shkonda.artschools.presentation.main_page.home.states.AllArtCategoriesState
import shkonda.artschools.presentation.main_page.home.states.ArtCategoryState
import shkonda.artschools.presentation.main_page.home.states.ArtTypesState
import shkonda.artschools.presentation.main_page.home.states.UserState
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

    HomeScreenContent(modifier, user, artCategory, artCategoryList, artTypeList, viewModel)
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    user: UserProfile?,
    artCategory: ArtCategory?,
    artCategoryList: List<ArtCategory>,
    artTypeList: List<ArtType>,
    viewModel: HomeViewModel
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
            TopBarSection(
                modifier = modifier,
                user = user,
                artCategory = artCategory,
                artCategoryList = artCategoryList,
                viewModel = viewModel
            )
        }
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
        modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.padding(start = 16.dp))

        CategoryImage(
            modifier = modifier,
            artCategory = artCategory,
            artCategoryList = artCategoryList,
            viewModel = viewModel
        )
        if (user != null) UserNameLevel(modifier = modifier, username = user.username)

        if (user != null) ProfilePoints(modifier = modifier, points = user.points)
    }
}

@Composable
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
}

@Composable
private fun UserNameLevel(modifier: Modifier, username: String) {
    Column(
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
    }
}

@Composable
fun ProfilePoints(modifier: Modifier, points: Int) {
    Column() {
        Text(
            text = "$points",
            fontSize = 24.sp
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

@Composable
fun ArtTypesSection(
    modifier: Modifier,
    artTypeList: List<ArtType>,
    onCategoryClick: (Long) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Категории",
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            ),
            modifier = modifier.padding(bottom = 16.dp)
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
                    artType = type
                )

            }
        }
    }
}

@Composable
fun TypeCard(
    modifier: Modifier,
    typeTitle: String,
    typeImageUrl: String,
    onCategoryClick: (Long) -> Unit,
    artType: ArtType
) {
    Column(
        modifier = modifier.padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .clickable { onCategoryClick(artType.id) },

            shape = RoundedCornerShape(8)
        ) {
            AsyncImage(
                modifier = modifier
                    .size(72.dp),
                model = loadImage(context = LocalContext.current, imageUrl = typeImageUrl),
                contentDescription = "Изображение категорий",
                contentScale = ContentScale.Crop
            )
        }
        Text(
//            text = categoryName,
            text = typeTitle,
            fontSize = 24.sp,
            modifier = modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun QuizCardBackground(modifier: Modifier, isQuizCard: Boolean) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(id = if (isQuizCard) R.drawable.quiz_back else R.drawable.categori_back),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CategoryInterface(modifier: Modifier, categoryName: String, onClick: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        CategoryCardButton(
            modifier = modifier,
            onClick = onClick
        )
        Text(
            modifier = modifier.padding(start = 32.dp, bottom = 24.dp),
            text = categoryName,
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
    }
}

@Composable
private fun CategoryCardButton(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .padding(start = 32.dp, top = 24.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.White),
                shape = RoundedCornerShape(8)
            ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_play_arrow),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
private fun CategoryImage(modifier: Modifier, artCategory: ArtCategory?, artCategoryList: List<ArtCategory>, viewModel: HomeViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding()
    ) {
        IconButton(onClick = { showDialog = true }) {
            if (artCategory != null) {
                AsyncImage(
                    modifier = modifier
                        .size(72.dp),
                    model = loadImage(
                        context = LocalContext.current,
                        imageUrl = artCategory.imageUrl
                    ),
                    contentDescription = "Изображение категорий",
                    contentScale = ContentScale.Crop
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
                                    viewModel.updateUserArtCategory(artCategory.id)
                                    showDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun ImageTextButton(text: String, iconUrl: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = iconUrl,
                contentDescription = "Иконка",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Добавляет пространство между иконкой и текстом
            Text(text)
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
