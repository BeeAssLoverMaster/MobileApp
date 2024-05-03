package shkonda.artschools.data.data_source.arts.type.entity

import com.google.gson.annotations.SerializedName

data class ArtTypesDto(
    @SerializedName("types")
    val artTypes: List<ArtType>
)

data class ArtType(
    @SerializedName("id")
    val id: Long,
    @SerializedName("typeName")
    val typeName: String,
    @SerializedName("imageName")
    val imageName: String,
    @SerializedName("artCategory")
    val artCategory: Int
)