package shkonda.artschools.domain.model.genre

data class Genres(
    val genres: ArrayList<Genre>
)

data class Genre(
    val id: String,
    val genreName: String,
//    val genreImage: String
)