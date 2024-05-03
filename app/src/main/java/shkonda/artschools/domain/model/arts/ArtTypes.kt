package shkonda.artschools.domain.model.arts


data class ArtTypes(
    val types: List<ArtType>
)

data class ArtType(
    val id: Long,
    val typeName: String,
    val imageName: String,
    val artCategory: Int
)