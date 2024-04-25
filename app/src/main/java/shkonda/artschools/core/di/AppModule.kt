package shkonda.artschools.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import shkonda.artschools.data.data_source.auth.api.AuthApi
import shkonda.artschools.data.data_source.category.api.CategoryApi
import shkonda.artschools.data.data_source.genre.api.GenreApi
import shkonda.artschools.data.data_source.user.api.UserApi
import javax.inject.Singleton

const val BASE_URL = "http://10.0.2.2:8080"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApi(): CategoryApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreApi(): GenreApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenreApi::class.java)
    }
}