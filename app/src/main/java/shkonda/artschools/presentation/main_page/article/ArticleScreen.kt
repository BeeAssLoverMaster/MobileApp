package shkonda.artschools.presentation.main_page.article

import android.graphics.BlurMaskFilter.Blur
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
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
import shkonda.artschools.domain.model.quizzes.ArticleImage
import shkonda.artschools.domain.model.quizzes.QuizArticle
import shkonda.artschools.presentation.main_page.article.states.getQuizArticleState

// Основная функция для отображения экрана статьи
@Composable
fun ArticleScreen(modifier: Modifier = Modifier, genreId: Long, quizId: Long, viewModel: ArticleViewModel = hiltViewModel()) {
    // Эффект, запускающий получение данных статьи по Id викторины при изменении quizId
    LaunchedEffect(quizId) { viewModel.getQuizArticleByQuizId(quizId) }
    // Обработчик нажатия кнопки "Назад", возвращающий на экран викторин
    BackHandler { Navigator.navigate(NavScreen.QuizzesScreen.createQuizRoute(genreId)) }
    // Получение состояния статьи из ViewModel
    val quizArticle = getQuizArticleState(viewModel)
    val scrollState = rememberScrollState()
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.background), contentDescription = null, modifier = Modifier.fillMaxSize().blur(16.dp), contentScale = ContentScale.Crop)
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)))
            // Основное содержимое экрана
            Box(modifier = modifier.fillMaxSize()) {
                Column(modifier = modifier.fillMaxSize().padding(bottom = 60.dp).verticalScroll(scrollState)) {
                    ArticleScreenContent(modifier = modifier.padding(horizontal = 16.dp), quizArticle)
                    Spacer(modifier = modifier.height(32.dp))
                }
                // Кнопка для начала викторины, если статья загружена
                if (quizArticle != null) {
                    ProgressButton(scrollState, quizId = quizId, userPoints = quizArticle.userPoints, totalPoints = quizArticle.totalPoints, isQuizTaken = quizArticle.userPoints > 0,modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(16.dp))
                }
            }
        }
    }
}
// Функция для отображения содержимого экрана статьи
@Composable
fun ArticleScreenContent(modifier: Modifier, quizArticle: QuizArticle?) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val imageSize = (screenWidth / 3) - 16.dp
    // Если статья загружена, отображаем её содержимое
    if (quizArticle != null) {
        Column(modifier = modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(32.dp))
            // Заголовок статьи и изображение автора
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = quizArticle.title, style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White, fontSize = textSize), textAlign = TextAlign.Center, modifier = Modifier.padding(end = 16.dp).weight(1f))
                if (quizArticle.artistId != -1L) { AsyncImage(model = loadImage(context = LocalContext.current, imageUrl = quizArticle.artistImage), contentDescription = "Изображение в статье", contentScale = ContentScale.Crop, modifier = modifier.size(imageSize).clip(CircleShape).border(width = 2.dp, brush = Brush.linearGradient(colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)), start = Offset(0f, 0f), end = Offset(0f, Float.POSITIVE_INFINITY)), shape = CircleShape) .clickable { Navigator.navigate(NavScreen.BioArticleScreen.createBioArticleRoute(quizArticle.artistId)) }) }
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Текст статьи
            val paragraphs = quizArticle.text.split("\\n")
            paragraphs.forEach { paragraph -> Text(text = paragraph, style = TextStyle(fontSize = textSize, color = Color.White, textIndent = TextIndent(firstLine = 20.sp), hyphens = Hyphens.Auto), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) }
            Spacer(modifier = modifier.height(16.dp))
            // Галерея изображений статьи
            ImageGallery(modifier = modifier, quizArticle.articleImages)
        }
    }
}
// Функция для отображения галереи изображений статьи
@Composable
fun ImageGallery(modifier: Modifier = Modifier, articleImageList: List<ArticleImage>) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<ArticleImage?>(null) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = (screenWidth / 2)
    // Диалоговое окно для показа выбранного изображения
    if (showDialog && selectedImage != null) {
        ImageDialog(modifier = modifier, image = selectedImage) {
            showDialog = false
        }
    }
    // Горизонтальный список изображений
    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items(articleImageList) { index ->
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
// Функция для отображения диалогового окна с изображением
@Composable
fun ImageDialog(modifier: Modifier, image: ArticleImage?, onDismiss: () -> Unit) {
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
// Функция для отображения кнопки прогресса
@Composable
fun ProgressButton(
    scrollState: ScrollState,
    quizId: Long,
    userPoints: Int,
    totalPoints: Int,
    isQuizTaken: Boolean,  // Добавлен новый параметр для проверки состояния теста
    modifier: Modifier
) {
    val progress = scrollState.value / scrollState.maxValue.toFloat()
    val buttonColor = if (scrollState.value >= scrollState.maxValue) Color.Green else Color.Gray
    val isEnabled = scrollState.value >= scrollState.maxValue

    val openDialog = remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 32).value.sp
    val titleSize = (screenWidth / 24).value.sp

    // Изменение текста в зависимости от того, был ли тест пройдена
    val buttonText = when {
        isQuizTaken -> "Пройти викторину снова / Прошлый результат: $userPoints / Макс: $totalPoints"
        else -> "Пройти викторину / Макс: $totalPoints"
    }
    Button(
        onClick = {
            openDialog.value = true
        },
        modifier = modifier.height(48.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val gradient = Brush.linearGradient(
                colors = listOf(Color(0xFF8372DB), Color(0xFF79B27D)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, 0f)
            )
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .matchParentSize()
                    .clip(shape = RoundedCornerShape(4.dp)),
                color = if (scrollState.value >= scrollState.maxValue) Color(0xFF8372DB) else Color(
                    0xFF79B27D
                ).copy(alpha = progress), // Светло-зелёный цвет для прогресса
                backgroundColor = Color.LightGray
            )
            Text(
                buttonText,
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = {
                    Text(
                        text = "Вы уверены, что хотите пройти тестирование?",
                        color = Color.LightGray,
                        fontSize = titleSize
                    )
                },
                text = {
                    Text(
                        buildAnnotatedString {
                            append("За прохождение этого теста вы можете заработать ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("$totalPoints")
                            }
                            append(" баллов!") }, color = Color.LightGray, fontSize = textSize)
                },
                confirmButton = {
                    Button({ Navigator.navigate(NavScreen.QuestionScreen.createQuestionRoute(quizId)) }, colors = ButtonDefaults.buttonColors(contentColor = Color.LightGray, backgroundColor = Color.DarkGray), border = BorderStroke(1.dp, Color.LightGray)) { Text("Начать", fontSize = 22.sp) }
                },
                dismissButton = {
                    Button(onClick = { openDialog.value = false }, colors = ButtonDefaults.buttonColors(contentColor = Color.LightGray, backgroundColor = Color.DarkGray),border = BorderStroke(1.dp, Color.LightGray)) { Text("Отмена", fontSize = 22.sp) }
                }, containerColor = Color.DarkGray, titleContentColor = Color.LightGray, textContentColor = Color.LightGray, iconContentColor = Color.LightGray
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun ArticleScreenContentPrev() { ArticleScreenContentForPrev(modifier = Modifier) }

@Composable
fun ArticleScreenContentForPrev(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 16).value.sp
    val imageSize = (screenWidth / 3) - 16.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Очень длинное название статьи",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = textSize
                ),
                modifier = Modifier
                    .padding(end = 16.dp) // Добавляем отступ справа от текста
                    .weight(1f) // Занимаем доступное пространство
            )
            Image(
                painter = painterResource(id = R.drawable.painting),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Black, shape = CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        /*Text(
            text = stringResource(id = R.string.lorem_ipsum),
            style = TextStyle(
                fontSize = textSize,
                textIndent = TextIndent(firstLine = 30.sp)
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )*/

        Spacer(modifier = modifier.height(16.dp))

        ImageGalleryForPrev()
    }
}

@Composable
fun ImageGalleryForPrev(modifier: Modifier = Modifier) {
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

/*@Composable
fun FormattedText(text: String, modifier: Modifier = Modifier) {
    val paragraphIndent = "    " // Четыре пробела для отступа
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textSize = (screenWidth / 24).value.sp

    val formattedText = buildAnnotatedString {
        text.split("\n").forEachIndexed { index, paragraph ->
            if (index != 0) {
                append("\n")
            }
            append(paragraphIndent)
            append(paragraph)
        }
    }

    BasicText(
        text = formattedText,
        modifier = modifier,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        overflow = TextOverflow.Ellipsis,
        softWrap = true,
        maxLines = Int.MAX_VALUE
    )
}*/

