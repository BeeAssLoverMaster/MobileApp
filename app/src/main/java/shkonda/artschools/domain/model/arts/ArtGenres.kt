package shkonda.artschools.domain.model.arts

data class ArtGenres(
    val genres: List<ArtGenre>
)

data class ArtGenre(
    val id: Long,
    val genreName: String,
    val imageNameUrl: String,
    val artTypeId: Long
)
