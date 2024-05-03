package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.quizzes.entity.QuizDto
import shkonda.artschools.domain.model.arts.ArtGenre
import shkonda.artschools.domain.model.arts.ArtGenres
import shkonda.artschools.domain.model.quizzes.Quiz
import shkonda.artschools.domain.model.quizzes.Quizzes

fun QuizDto.toQuiz(): Quizzes {
    return Quizzes(
        quizzesList = this.quizzesList.map { dto ->
            Quiz(
                id = dto.id,
                artGenreId = dto.artGenreId,
                articleId = dto.articleId,
                articleTitle = dto.articleTitle
            )
        }
    )
}