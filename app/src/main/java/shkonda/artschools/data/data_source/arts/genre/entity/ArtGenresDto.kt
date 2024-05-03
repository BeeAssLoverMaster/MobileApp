package shkonda.artschools.data.data_source.arts.genre.entity

import com.google.gson.annotations.SerializedName

data class ArtGenresDto(
    @SerializedName("genres")
    val artTypes: List<ArtGenre>
)

data class ArtGenre(
    @SerializedName("id")
    val id: Long,
    @SerializedName("genreName")
    val genreName: String,
    @SerializedName("imageName")
    val typeNameUrl: String,
    @SerializedName("artTypeId")
    val artTypeId: Long
)