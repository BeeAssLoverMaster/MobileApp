package shkonda.artschools.data.repository.artist

import shkonda.artschools.data.data_source.artists.ArtistRemoteDataSource
import shkonda.artschools.data.mappers.toArtist
import shkonda.artschools.data.mappers.toSimpleArtist
import shkonda.artschools.domain.model.artist.Artist
import shkonda.artschools.domain.model.artist.SimpleArtists
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(private val remoteDataSource: ArtistRemoteDataSource): ArtistRepository {
    override suspend fun getArtistByArticleId(articleId: Long): Artist =
        remoteDataSource.getArtistByArticleId(articleId).toArtist()

    override suspend fun getAllArtistByGenreId(genreId: Long): SimpleArtists =
        remoteDataSource.getAllArtistByGenreId(genreId).toSimpleArtist()
}