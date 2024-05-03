package shkonda.artschools.presentation.main_page.quizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.domain.usecase.quizzes.GetQuizUseCase
import shkonda.artschools.presentation.main_page.art_genres.ArtGenresState
import javax.inject.Inject

@HiltViewModel
class QuizzesViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizUseCase
) : ViewModel() {
    private val _quizState = MutableStateFlow<QuizState>(QuizState.Loading)
    val quizState = _quizState.asStateFlow()

    fun getQuizzesByGenreId(genreId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getQuizzesUseCase(genreId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _quizState.value = QuizState.Loading
                }

                is Response.Success -> {
                    _quizState.value = QuizState.Success(quizData = response.data)
                }

                is Response.Error -> {
                    _quizState.value = QuizState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}
