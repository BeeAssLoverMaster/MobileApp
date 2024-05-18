package shkonda.artschools.presentation.main_page.quizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.domain.usecase.articles.GetArticleUseCase
import shkonda.artschools.domain.usecase.artist.GetArtistUseCase
import shkonda.artschools.domain.usecase.quizzes.GetHistoryQuizUseCase
import shkonda.artschools.domain.usecase.quizzes.GetQuizUseCase
import shkonda.artschools.domain.usecase.quizzes.GetTechniqueQuizUseCase
import shkonda.artschools.presentation.main_page.quizzes.states.ArticleState
import shkonda.artschools.presentation.main_page.quizzes.states.ArtistState
import shkonda.artschools.presentation.main_page.quizzes.states.HistoryQuizState
import shkonda.artschools.presentation.main_page.quizzes.states.QuizState
import shkonda.artschools.presentation.main_page.quizzes.states.SimpleArtistState
import shkonda.artschools.presentation.main_page.quizzes.states.TechniqueQuizState
import javax.inject.Inject

@HiltViewModel
class QuizzesViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizUseCase,
    private val getHistoryQuizList: GetHistoryQuizUseCase,
    private val getTechniqueQuizUseCase: GetTechniqueQuizUseCase,
    private val getArtistUseCase: GetArtistUseCase,
) : ViewModel() {
    private val _quizState = MutableStateFlow<QuizState>(QuizState.Loading)
    val quizState = _quizState.asStateFlow()

    private val _historyQuizState = MutableStateFlow<HistoryQuizState>(HistoryQuizState.Loading)
    val historyQuizState = _historyQuizState.asStateFlow()

    private val _techniqueQuizState = MutableStateFlow<TechniqueQuizState>(TechniqueQuizState.Loading)
    val techniqueQuizState = _techniqueQuizState.asStateFlow()

    //
    private val _articleState = MutableStateFlow<ArticleState>(ArticleState.Loading)
    val articleState = _articleState.asStateFlow()

    private val _simpleArticleState = MutableStateFlow<SimpleArtistState>(SimpleArtistState.Loading)
    val simpleArtistState = _simpleArticleState.asStateFlow()

    private val _artistState = MutableStateFlow<ArtistState>(ArtistState.Loading)
    val artistState = _artistState.asStateFlow()

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

    fun getAllArtistByGenreId(genreId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getArtistUseCase(genreId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _simpleArticleState.value = SimpleArtistState.Loading
                }

                is Response.Success -> {
                    _simpleArticleState.value =
                        SimpleArtistState.Success(simpleArtistData = response.data)
                }

                is Response.Error -> {
                    _simpleArticleState.value =
                        SimpleArtistState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun getAllHistoryArticle(genreId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getHistoryQuizList(genreId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _historyQuizState.value = HistoryQuizState.Loading
                }

                is Response.Success -> {
                    _historyQuizState.value =
                        HistoryQuizState.Success(historyQuizData = response.data)
                }

                is Response.Error -> {
                    _historyQuizState.value =
                        HistoryQuizState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun getAllTechniqueArticle(genreId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getTechniqueQuizUseCase(genreId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _techniqueQuizState.value = TechniqueQuizState.Loading
                }

                is Response.Success -> {
                    _techniqueQuizState.value =
                        TechniqueQuizState.Success(techniqueQuizData = response.data)
                }

                is Response.Error -> {
                    _techniqueQuizState.value =
                        TechniqueQuizState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}
