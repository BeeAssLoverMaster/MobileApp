package shkonda.artschools.data.data_source.artists

import shkonda.artschools.data.data_source.artists.entity.ArtistDto
import shkonda.artschools.data.data_source.artists.entity.SimpleArtistsDto

interface ArtistRemoteDataSource {
    suspend fun getArtistByArticleId(articleId: Long): ArtistDto

    suspend fun getAllArtistByGenreId(genreId: Long): SimpleArtistsDto
}