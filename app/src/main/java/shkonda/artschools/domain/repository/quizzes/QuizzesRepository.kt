package shkonda.artschools.domain.repository.quizzes

import shkonda.artschools.domain.model.arts.ArtGenres
import shkonda.artschools.domain.model.quizzes.Quizzes

interface QuizzesRepository {
    suspend fun getQuizzesByArtGenreId(genreId: Long): Quizzes
}