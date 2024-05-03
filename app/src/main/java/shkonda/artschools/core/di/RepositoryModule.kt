package shkonda.artschools.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shkonda.artschools.data.repository.AuthRepositoryImpl
import shkonda.artschools.data.repository.arts.ArtCategoryRepositoryImpl
import shkonda.artschools.data.repository.UserRepositoryImpl
import shkonda.artschools.data.repository.arts.ArtGenresRepositoryImpl
import shkonda.artschools.data.repository.arts.ArtTypesRepositoryImpl
import shkonda.artschools.data.repository.quizzes.QuizRepositoryImpl
import shkonda.artschools.domain.repository.AuthRepository
import shkonda.artschools.domain.repository.arts.ArtCategoryRepository
import shkonda.artschools.domain.repository.UserRepository
import shkonda.artschools.domain.repository.arts.ArtGenresRepository
import shkonda.artschools.domain.repository.arts.ArtTypesRepository
import shkonda.artschools.domain.repository.quizzes.QuizzesRepository
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
    abstract fun bindQuizRepository(quizRepositoryImpl: QuizRepositoryImpl): QuizzesRepository

}