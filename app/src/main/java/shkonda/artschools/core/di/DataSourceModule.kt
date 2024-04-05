package shkonda.artschools.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shkonda.artschools.data.data_source.auth.AuthRemoteDataSource
import shkonda.artschools.data.data_source.auth.AuthRemoteDataSourceImpl
import shkonda.artschools.data.data_source.user.UserRemoteDataSource
import shkonda.artschools.data.data_source.user.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource
}