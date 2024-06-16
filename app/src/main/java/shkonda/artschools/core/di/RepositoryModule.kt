package shkonda.artschools.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shkonda.artschools.data.repository.AuthRepositoryImpl
import shkonda.artschools.data.repository.art.ArtCategoryRepositoryImpl
import shkonda.artschools.data.repository.UserRepositoryImpl
import shkonda.artschools.data.repository.art.ArtGenresRepositoryImpl
import shkonda.artschools.data.repository.art.ArtTypesRepositoryImpl
import shkonda.artschools.data.repository.quiz.QuizRepositoryImpl
import shkonda.artschools.data.repository.AuthRepository
import shkonda.artschools.data.repository.art.ArtCategoryRepository
import shkonda.artschools.data.repository.UserRepository
import shkonda.artschools.data.repository.article.ArticleRepository
import shkonda.artschools.data.repository.article.ArticleRepositoryImpl
import shkonda.artschools.data.repository.artist.ArtistRepository
import shkonda.artschools.data.repository.artist.ArtistRepositoryImpl
import shkonda.artschools.data.repository.art.ArtGenresRepository
import shkonda.artschools.data.repository.art.ArtTypesRepository
import shkonda.artschools.data.repository.question.QuestionRepository
import shkonda.artschools.data.repository.question.QuestionRepositoryImpl
import shkonda.artschools.data.repository.quiz.QuizRepository
import shkonda.artschools.data.repository.schools.SchoolRepository
import shkonda.artschools.data.repository.schools.SchoolRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    //arts
    @Binds
    @Singleton
    abstract fun bindArtCategoryRepository(artCategoryRepositoryImpl: ArtCategoryRepositoryImpl): ArtCategoryRepository

    @Binds
    @Singleton
    abstract fun bindArtTypesRepository(artTypesRepositoryImpl: ArtTypesRepositoryImpl): ArtTypesRepository

    @Binds
    @Singleton
    abstract fun bindArtGenresRepository(artGenresRepositoryImpl: ArtGenresRepositoryImpl): ArtGenresRepository

    //quizzes
    @Binds
    @Singleton
    abstract fun bindQuizRepository(quizRepositoryImpl: QuizRepositoryImpl): QuizRepository

    //articles
    @Binds
    @Singleton
    abstract fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository

    //artists
    @Binds
    @Singleton
    abstract fun bindArtistRepository(artistRepositoryImpl: ArtistRepositoryImpl): ArtistRepository

    //quesions
    @Binds
    @Singleton
    abstract fun bindQuestionRepository(questionRepositoryImpl: QuestionRepositoryImpl): QuestionRepository

    //schools
    @Binds
    @Singleton
    abstract fun bindSchoolRepository(schoolRepositoryImpl: SchoolRepositoryImpl): SchoolRepository

}