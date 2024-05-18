package shkonda.artschools.domain.model.arts

data class ArtCategories(
    val artCategories: List<ArtCategory>
)
data class ArtCategory(
    val id: Long,
    val categoryName: String,
    val imageUrl: String
)