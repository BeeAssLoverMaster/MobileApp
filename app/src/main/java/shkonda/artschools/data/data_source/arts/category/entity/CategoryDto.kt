package shkonda.artschools.data.data_source.arts.category.entity

import com.google.gson.annotations.SerializedName
import shkonda.artschools.data.data_source.arts.type.entity.ArtType

data class ArtCategoriesDto(
    @SerializedName("categories")
    val artCategories: List<ArtCategoryDto>
)
data class ArtCategoryDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("categoryName")
    val categoryName: String,

    @SerializedName("imageName")
    val categoryImageUrl: String
)