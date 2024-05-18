package shkonda.artschools.data.data_source.artists

import shkonda.artschools.data.data_source.artists.api.ArtistApi
import shkonda.artschools.data.data_source.artists.entity.ArtistDto
import shkonda.artschools.data.data_source.artists.entity.SimpleArtistsDto
import javax.inject.Inject

class ArtistRemoteDataSourceImpl @Inject constructor(private val api: ArtistApi) : ArtistRemoteDataSource {
    override suspend fun getArtistByArticleId(articleId: Long): ArtistDto =
        api.getArtistByArticleId(articleId)

    override suspend fun getAllArtistByGenreId(genreId: Long): SimpleArtistsDto =
        api.getAllArtistByGenreId(genreId)
}