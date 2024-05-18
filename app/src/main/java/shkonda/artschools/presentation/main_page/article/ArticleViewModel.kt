package shkonda.artschools.presentation.main_page.article

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.getToken
import shkonda.artschools.domain.usecase.articles.GetBioArticleUseCase
import shkonda.artschools.domain.usecase.artist.GetArtistUseCase
import shkonda.artschools.domain.usecase.quizzes.GetQuizArticleUseCase
import shkonda.artschools.presentation.main_page.article.states.BioArticleState
import shkonda.artschools.presentation.main_page.article.states.QuizArticleState
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArtistUseCase: GetArtistUseCase,
    private val getQuizArticleUseCase: GetQuizArticleUseCase,
    private val getBioArticleUseCase: GetBioArticleUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _quizArticleState = MutableStateFlow<QuizArticleState>(QuizArticleState.Loading)
    val quizArticleState = _quizArticleState.asStateFlow()

    private val _bioArticleState = MutableStateFlow<BioArticleState>(BioArticleState.Loading)
    val bioArticleState = _bioArticleState.asStateFlow()

    private var token: String? = null
    init {
        token = sharedPreferences.getToken()
    }

    fun getQuizArticleByQuizId(quizId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getQuizArticleUseCase(token = "Bearer $token", quizId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _quizArticleState.value = QuizArticleState.Loading
                }

                is Response.Success -> {
                    _quizArticleState.value =
                        QuizArticleState.Success(quizArticleData = response.data)
                }

                is Response.Error -> {
                    _quizArticleState.value =
                        QuizArticleState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun getBioArticleByArtistId(artistId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getBioArticleUseCase(artistId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _bioArticleState.value = BioArticleState.Loading
                }

                is Response.Success -> {
                    _bioArticleState.value =
                        BioArticleState.Success(bioArticleData = response.data)
                }

                is Response.Error -> {
                    _bioArticleState.value =
                        BioArticleState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}