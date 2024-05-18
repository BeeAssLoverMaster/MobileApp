package shkonda.artschools.data.repository.artist

import shkonda.artschools.domain.model.artist.Artist
import shkonda.artschools.domain.model.artist.SimpleArtists

interface ArtistRepository {
    suspend fun getArtistByArticleId(articleId: Long): Artist
    suspend fun getAllArtistByGenreId(genreId: Long): SimpleArtists
}