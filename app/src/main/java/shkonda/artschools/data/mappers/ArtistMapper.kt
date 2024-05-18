package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.artists.entity.ArtistDto
import shkonda.artschools.data.data_source.artists.entity.SimpleArtistsDto
import shkonda.artschools.domain.model.artist.Artist
import shkonda.artschools.domain.model.artist.SimpleArtist
import shkonda.artschools.domain.model.artist.SimpleArtists

fun ArtistDto.toArtist(): Artist {
    return Artist(
        id = this.id,
        artistName = this.artistName,
        imageUrl = this.imageUrl,
        articleId = this.articleId
    )
}

fun SimpleArtistsDto.toSimpleArtist(): SimpleArtists {
    return SimpleArtists(
        artistList = this.artisList.map { dto ->
            SimpleArtist(
                quizId = dto.quizId,
                artistId = dto.artistId,
                imageUrl = dto.imageUrl,
            )
        },
    )
}