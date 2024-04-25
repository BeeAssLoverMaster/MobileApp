package shkonda.artschools.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shkonda.artschools.data.repository.AuthRepositoryImpl
import shkonda.artschools.data.repository.CategoriesRepositoryImpl
import shkonda.artschools.data.repository.GenresRepositoryImpl
import shkonda.artschools.data.repository.UserRepositoryImpl
import shkonda.artschools.domain.repository.AuthRepository
import shkonda.artschools.domain.repository.CategoriesRepository
import shkonda.artschools.domain.repository.GenresRepository
import shkonda.artschools.domain.repository.UserRepository
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

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(categoriesRepositoryImpl: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    @Singleton
    abstract fun bindGenresRepository(genresRepositoryImpl: GenresRepositoryImpl): GenresRepository
}