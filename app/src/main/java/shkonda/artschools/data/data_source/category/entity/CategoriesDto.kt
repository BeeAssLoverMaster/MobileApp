package shkonda.artschools.data.data_source.category.entity

import com.google.gson.annotations.SerializedName

data class CategoriesDto (
    @SerializedName("categories")
    val categories: ArrayList<CategoryDto>
)

data class CategoryDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("categoryName")
    val categoryName: String,

    @SerializedName("imageUrl")
    val categoryImage: String
)
