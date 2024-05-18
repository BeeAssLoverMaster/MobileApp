package shkonda.artschools.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import shkonda.artschools.data.data_source.articles.api.ArticleApi
import shkonda.artschools.data.data_source.artists.api.ArtistApi
import shkonda.artschools.data.data_source.auth.api.AuthApi
import shkonda.artschools.data.data_source.arts.category.api.ArtCategoryApi
import shkonda.artschools.data.data_source.arts.genre.api.ArtGenresApi
import shkonda.artschools.data.data_source.arts.type.api.ArtTypesApi
import shkonda.artschools.data.data_source.question.api.QuestionApi
import shkonda.artschools.data.data_source.quizzes.api.QuizApi
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
    fun provideArtCategoryApi(): ArtCategoryApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtCategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArtTypesApi(): ArtTypesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtTypesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArtGenresApi(): ArtGenresApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtGenresApi::class.java)
    }

    //quizzes
    @Provides
    @Singleton
    fun provideQuizApi(): QuizApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }

    //articles
    @Provides
    @Singleton
    fun provideArticleApi(): ArticleApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApi::class.java)
    }

    //artists
    @Provides
    @Singleton
    fun provideArtistApi(): ArtistApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtistApi::class.java)
    }

    //questions
    @Provides
    @Singleton
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}