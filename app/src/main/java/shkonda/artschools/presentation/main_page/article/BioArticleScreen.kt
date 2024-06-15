package shkonda.artschools.presentation.main_page.article

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.Sunset
import shkonda.artschools.domain.model.article.ArtistArticle
import shkonda.artschools.domain.model.article.BioArticleImage
import shkonda.artschools.presentation.main_page.article.states.getBioArticleState
import shkonda.artschools.presentation.main_page.article.states.getQuizArticleState

@Composable
fun BioArticleScreen(
    modifier: Modifier = Modifier,
    artistId: Long,
    quizId: Long,
    viewModel: ArticleViewModel = hiltViewModel()
) {
    LaunchedEffect(artistId) {
        viewModel.getBioArticleByArtistId(artistId)
    }

    BackHandler {
        Navigator.navigate(NavScreen.ArticleScreen.createArticleRoute(quizId))
    }

    val quizArticle = getQuizArticleState(viewModel)
    val bioArticle = getBioArticleState(viewModel)
    val scrollState = rememberScrollState()

    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().blur(16.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
            )
            Box(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp)
                        .verticalScroll(scrollState)
                ) {
                    BioArticleScreenContent(modifier = modifier.padding(horizontal = 16.dp), bioArticle)
//                    Spacer(modifier = modifier.height(32.dp))
                }
            }
        }

    }

}


@Composable
fun BioArticleScreenContent(
    modifier: Modifier,
    bioArticle: ArtistArticle?
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val imageSize = (screenWidth / 3) - 16.dp
    if (bioArticle != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bioArticle.artistName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = textSize
                    ),
                    modifier = Modifier
                        .padding(end = 16.dp) // Добавляем отступ справа от текста
                        .weight(1f)
                )
                AsyncImage(
                    model = loadImage(context = LocalContext.current, imageUrl = bioArticle.artistImage),
                    contentDescription = "Изображение в статье",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(imageSize)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                                start = Offset(0f, 0f), // Top-center
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            ),
                            shape = CircleShape
                        )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            val paragraphs = bioArticle.text.split("\\n")
            paragraphs.forEach { paragraph ->
                Text(
                    text = paragraph,
                    style = TextStyle(
                        fontSize = textSize,
                        color = Color.White,
                        textIndent = TextIndent(firstLine = 60.sp),
                        hyphens = Hyphens.Auto
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            BioImageGallery(modifier = modifier, bioArticle.bioArticleImages)
        }
    }

}

@Composable
fun BioImageGallery(modifier: Modifier = Modifier, bioArticleImageList: List<BioArticleImage>) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<BioArticleImage?>(null) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = (screenWidth / 2)

    if (showDialog && selectedImage != null) {
        BioImageDialog(modifier = modifier, image = selectedImage) {
            showDialog = false
        }
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items(bioArticleImageList) { index ->
            AsyncImage(
                modifier = modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(4.dp))
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                            start = Offset(0f, 0f), // Top-center
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        selectedImage = index
                        showDialog = true
                    },
                model = loadImage(context = LocalContext.current, imageUrl = index.imageName),
                contentDescription = index.imageDescription,
                contentScale = ContentScale.Crop
            )
        }
    }

}

@Composable
fun BioImageDialog(modifier: Modifier, image: BioArticleImage?, onDismiss: () -> Unit) {
    if (image != null) {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                AsyncImage(
                    model = loadImage(context = LocalContext.current, imageUrl = image.imageName),
                    contentDescription = image.imageDescription,
                    contentScale = ContentScale.FillWidth,  // Изображение заполняет всю ширину
                    modifier = Modifier
                        .fillMaxWidth()  // Максимально возможная ширина
                )
                Text(
                    text = image.imageDescription,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(all = 16.dp)  // Отступы вокруг текста
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleScreenContentPrev() {
    BioArticleScreenContentForPrev(modifier = Modifier)
}

@Composable
fun BioArticleScreenContentForPrev(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Название статьи очень длинное",
                fontSize = 24.sp,
                modifier = Modifier.weight(2f)
            )
            Image(
                painter = painterResource(id = R.drawable.painting),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(125.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Black, shape = CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(id = R.string.lorem_ipsum),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = modifier.height(16.dp))

        BioImageGalleryForPrev()
    }
}

@Composable
fun BioImageGalleryForPrev(modifier: Modifier = Modifier) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(10) { index ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                modifier = modifier.padding(vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.painting),
                    contentDescription = "GalleryImage $index",
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier
                        .size(200.dp)
                )
            }
        }
    }
}