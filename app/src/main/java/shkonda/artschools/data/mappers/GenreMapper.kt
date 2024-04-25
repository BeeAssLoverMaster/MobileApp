package shkonda.artschools.data.mappers

import shkonda.artschools.data.data_source.genre.entity.GenreDto
import shkonda.artschools.domain.model.genre.Genre
import shkonda.artschools.domain.model.genre.Genres

fun List<GenreDto>.toGenres(): Genres {
    return Genres(
        genres = this.map { dto ->
            Genre(
                id = dto.id,
                genreName = dto.genreName,
//                genreImage = dto.genreImage
            )
        } as ArrayList<Genre>
    )
}