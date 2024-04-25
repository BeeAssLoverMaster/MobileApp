package shkonda.artschools.domain.model.category

data class Categories(
    val categories: ArrayList<Category>
)

data class Category(
    val id: String,
    val categoryName: String,
    val categoryImage: String
)
