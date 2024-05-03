package shkonda.artschools.data.data_source.arts.category.entity

import com.google.gson.annotations.SerializedName
data class ArtCategoryDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("categoryName")
    val categoryName: String,

    @SerializedName("imageName")
    val categoryImageUrl: String
)