package shkonda.artschools.presentation.main_page.questions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.theme.Black
import shkonda.artschools.core.ui.theme.Grapefruit
import shkonda.artschools.core.ui.theme.LightBrown
import shkonda.artschools.core.ui.theme.LightPink
import shkonda.artschools.core.ui.theme.LightPurple
import shkonda.artschools.core.ui.theme.LightYellow
import shkonda.artschools.core.ui.theme.StrangeOrange
import shkonda.artschools.core.ui.theme.StrangeRed
import shkonda.artschools.core.ui.theme.Sunset
import shkonda.artschools.domain.model.question.Answer
import shkonda.artschools.domain.model.question.Question
import shkonda.artschools.presentation.main_page.questions.states.getQuestionListState

private val gradientColors = listOf(
    LightPurple,
    LightPink,
    StrangeRed,
    StrangeOrange,
    LightBrown,
    LightYellow
)
/*

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    quizId: Long,
    viewModel: QuestionViewModel = hiltViewModel()
) {
    LaunchedEffect(quizId) {
        viewModel.getQuestionListByQuizId(quizId)
    }

    BackHandler {

    }

    val questionList = getQuestionListState(viewModel)

    var selectedOptionId by rememberSaveable { mutableStateOf("") }

    QuestionScreenContent(
        modifier = modifier,
        questionList = questionList,
        viewModel = viewModel,
        onChecked = { selectedOptionId = it },
        onPreviousClicked = {
            selectedOptionId = ""
            //viewmodel.goPrev
        }
    ) {

    }

}

@Composable
fun QuestionScreenContent(
    modifier: Modifier,
    questionList: List<Question>,
    viewModel: QuestionViewModel,
    onChecked: (String) -> Unit,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

    }
}

@Composable
fun QuestionSection(
    modifier: Modifier,
    questionList: List<Question>,
    viewModel: QuestionViewModel,
    selectedOptionId: String,
    onChecked: (String) -> Unit,
//    onPreviousClicked: () -> Unit,
//    onNextClicked: (StartedQuiz) -> Unit
) {
    //Timer

    Column(
        modifier = modifier
    ) {
        questionList.forEach { question ->
            QuestionText(modifier = modifier, questionText = question.question)

            AnswersSection(
                modifier = modifier,
                answerList = question.answerList,
                onChecked = onChecked,
            )
        }
    }
}

@Composable
fun QuestionText(
    modifier: Modifier,
    questionText: String
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = questionText,
        style = MaterialTheme.typography.h2.copy(color = Color.Black, fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun AnswersSection(
    modifier: Modifier,
    answerList: List<Answer>,
    onChecked: (String) -> Unit,
//    checked: (String) -> Boolean
) {
    Column(
        modifier = modifier.padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        answerList.forEach {
            Answer(
                modifier = modifier.padding(vertical = 8.dp),
                answerText = it.description,
                onChecked = { onChecked(it.optionId) },
                checked = checked(it.optionId)
            )
        }
    }
}

@Composable
private fun Answer(
    modifier: Modifier,
    answerText: String,
    onChecked: () -> Unit,
    checked: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TextFieldDefaults.MinHeight + 8.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Black),
                shape = RoundedCornerShape(25)
            )
            .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(25))
            .clickable(enabled = true, onClick = {}),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.padding(start = 16.dp),
            text = answerText,
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primaryVariant)
        )
        CircleCheckbox(
            modifier = modifier,
            onChecked = onChecked,
            selected = checked
        )
    }
}

@Composable
fun CircleCheckbox(
    modifier: Modifier,
    onChecked: () -> Unit,
    selected: Boolean = false,
    tint: Color = if (selected) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
) {

    val imageVector =
        if (selected) R.drawable.ic_baseline_check_circle else R.drawable.ic_outline_circle_24
    val background = if (selected) MaterialTheme.colors.background else Color.Transparent

    IconButton(onClick = onChecked) {
        Icon(
            modifier = modifier.background(color = background, shape = CircleShape),
            painter = painterResource(id = imageVector),
            contentDescription = null,
            tint = tint
        )
    }
}*/

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    quizId: Long,
    viewModel: QuestionViewModel = hiltViewModel()
) {
    LaunchedEffect(quizId) {
        viewModel.getQuestionListByQuizId(quizId)
    }

    BackHandler {
        // Обработка нажатия системной кнопки "Назад"
    }

    val questionList = getQuestionListState(viewModel)
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    var selectedOptionId by rememberSaveable { mutableStateOf("") }
    var totalPoints by rememberSaveable { mutableStateOf(0) }
    var lastSelectedAnswerCorrect by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        if (questionList.isNotEmpty()) {
            val question = questionList[currentQuestionIndex]
            QuestionContent(question = question)
            OptionsGrid(
                options = question.answerList,
                selectedOption = selectedOptionId,
                onOptionSelected = { answer ->
                    selectedOptionId = answer.answer
                    lastSelectedAnswerCorrect = answer.isCorrect
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            NextQuestionButton(
                enabled = selectedOptionId.isNotEmpty(),
                isLastQuestion = currentQuestionIndex == questionList.size - 1,
                onClick = {
                    // Начислите очки, если последний выбранный ответ был правильным
                    if (lastSelectedAnswerCorrect) {
                        totalPoints += question.questionValue
                        println(totalPoints)
                    }
                    // Переход к следующему вопросу или завершение викторины
                    if (currentQuestionIndex < questionList.size - 1) {
                        currentQuestionIndex++
                        selectedOptionId = ""
                        lastSelectedAnswerCorrect = false
                    } else {
                        viewModel.addPointsToUser(totalPoints, quizId) {
                            Navigator.navigate(NavScreen.ArticleScreen.createArticleRoute(quizId))
                        } // Отправка накопленных очков
                    }
                }
            )
        } else {
            CircularProgressIndicator() // Показать индикатор загрузки
        }
    }
}

@Composable
fun QuestionContent(question: Question) {
    Column {
        Text(text = question.question, style = MaterialTheme.typography.h6)
        question.questionImageList.firstOrNull()?.let {
            AsyncImage(
                model = loadImage(context = LocalContext.current, imageUrl = it.questionImage),
                contentDescription = "Изображение в статье",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(125.dp)
                    .clip(CircleShape)
                    .border(
                        border = BorderStroke(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Sunset,
                                    Grapefruit
                                )
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun OptionsGrid(options: List<Answer>, selectedOption: String, onOptionSelected: (Answer) -> Unit) {
    Column {
        options.forEach { answer ->
            OptionButton(
                text = answer.answer,
                isSelected = selectedOption == answer.answer,
                onClick = { onOptionSelected(answer) }
            )
        }
    }
}

@Composable
fun OptionButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color.LightGray else Color.White // Выделение выбранного ответа
    val textColor = if (isSelected) Color.Black else Color.Gray // Изменение цвета текста при выборе

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(text, color = textColor)
    }
}

@Composable
fun NextQuestionButton(enabled: Boolean, isLastQuestion: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)
    ) {
        val buttonText = if (isLastQuestion) "Завершить викторину" else "Следующий вопрос"
        Text(buttonText, color = Color.Black)
    }
}


