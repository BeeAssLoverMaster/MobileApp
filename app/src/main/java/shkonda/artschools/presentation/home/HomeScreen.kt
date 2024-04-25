package shkonda.artschools.presentation.home

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.OnBackPressed
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.Sunset
import shkonda.artschools.domain.model.category.Categories
import shkonda.artschools.domain.model.category.Category

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val categoriesState by viewModel.categoriesState.collectAsState()

    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    HomeScreenContent(modifier, userState, categoriesState = categoriesState, viewModel)
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    userState: UserState,
    categoriesState: CategoriesState,
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
            TopBarSection(modifier = modifier, userState = userState)
        }
        QuizCategoriesSection(
            modifier = modifier,
            categoriesState = categoriesState,
            onCategoryClick = {categoryId ->
//                println(category)
                viewModel.onCategorySelected(categoryId)
                Navigator.navigate(NavScreen.GenresScreen.createRoute(categoryId))
            }
        )
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
                    CustomLoadingSpinner()
                }
            }

            is UserState.Success -> {
                ProfileImage(
                    modifier = modifier,
                    userProfileImage = userState.data.profilePictureUrl
                )
                UserNameLevel(
                    modifier = modifier,
//                    username = userState.data.username
                    username = userState.data.userName
                )
                //Notifications
            }

            is UserState.Error -> {
                TopBarError(
                    modifier = modifier,
                    errorMessage = userState.errorMessage
                )
            }

            else -> {}
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
private fun UserNameLevel(modifier: Modifier, username: String) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello, $username",
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

val fakeCategoriesList = listOf(
    Category("1", "Test1", ""),
    Category("2", "Test2", ""),
    Category("3", "Test3", ""),
    Category("4", "Test4", " "),
)
val fakeCategories = Categories(categories = ArrayList(fakeCategoriesList))
val fakeState = CategoriesState.Success(fakeCategories)

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun QuizPrev() {
    QuizCategoriesSection(
        modifier = Modifier,
        categoriesState = fakeState,
        onCategoryClick = {}
    )
}

@Composable
fun QuizCategoriesSection(
    modifier: Modifier,
    categoriesState: CategoriesState,
    onCategoryClick: (String) -> Unit
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

        when (categoriesState) {
            is CategoriesState.Loading -> {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is CategoriesState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(categoriesState.data.categories) {category ->
                        CategoryCard(
                            modifier = modifier,
                            categoryName = category.categoryName,
                            categoryImage = category.categoryImage,
                            onCategoryClick = onCategoryClick,
                            category = category
                        )

                    }
                }
            }
            is CategoriesState.Error -> {
                CategoriesError(modifier = modifier, errorMessage = categoriesState.errorMessage)
            }

            else -> {}
        }
    }
}

@Composable
fun CategoryCard(
    modifier: Modifier,
    categoryName: String,
    categoryImage: String,
    onCategoryClick: (String) -> Unit,
    category: Category
) {
    Column(
        modifier = modifier.padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .clickable { onCategoryClick(category.id) },
            shape = RoundedCornerShape(8)
        ) {
            AsyncImage(
                modifier = modifier
                    .size(72.dp),
//                model = loadImage(context = LocalContext.current, imageUrl = categoryImage),
                model = loadImage(context = LocalContext.current, imageUrl = category.categoryImage),
                contentDescription = "Изображение категорий",
                contentScale = ContentScale.Crop
            )
        }
        Text(
//            text = categoryName,
            text = category.categoryName,
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
private fun CategoryImage(modifier: Modifier, image: Int) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Изображение категорий",
            contentScale = ContentScale.Crop,
            modifier = modifier.size(128.dp)
        )
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