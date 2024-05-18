package shkonda.artschools.presentation.main_page.questions

import android.content.SharedPreferences
import android.util.Log
import android.util.Printer
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getToken
import shkonda.artschools.data.repository.UserRepository
import shkonda.artschools.domain.usecase.question.GetAllQuestionUseCase
import shkonda.artschools.domain.usecase.user.AddPointsUseCase
import shkonda.artschools.presentation.main_page.article.states.QuizArticleState
import shkonda.artschools.presentation.main_page.questions.states.QuestionListState
import javax.inject.Inject

@HiltViewModel
open class QuestionViewModel @Inject constructor(
    private val getAllQuestionUseCase: GetAllQuestionUseCase,

    private val sharedPreferences: SharedPreferences,
    private val addPointsUseCase: AddPointsUseCase
) : ViewModel() {
    private val _questionListState = MutableStateFlow<QuestionListState>(QuestionListState.Loading)
    open val questionListState = _questionListState.asStateFlow()

    private var token: String? = null

    init {
        getToken()
    }

    private fun getToken() {
        token = sharedPreferences.getToken()
    }

    open fun getQuestionListByQuizId(quizId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getAllQuestionUseCase(quizId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _questionListState.value = QuestionListState.Loading
                }

                is Response.Success -> {
                    _questionListState.value =
                        QuestionListState.Success(questionListData = response.data)
                }

                is Response.Error -> {
                    _questionListState.value =
                        QuestionListState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun addPointsToUser(points: Int, quizId: Long, onPointsAdded: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        addPointsUseCase(token = "Bearer $token", points, quizId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _questionListState.value = QuestionListState.Loading
                }

                is Response.Success -> {
                    Log.d("Question", "Очки зачислены успешно")
                    onPointsAdded()
                }

                is Response.Error -> {
                    _questionListState.value =
                        QuestionListState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}
