package shkonda.artschools.data.data_source.artists.entity

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("articleId")
    val articleId: Long
)
data class SimpleArtistsDto(
    @SerializedName("artists")
    val artisList: List<SimpleArtist>
)

data class SimpleArtist(
    @SerializedName("quizId")
    val quizId: Long,
    @SerializedName("artistId")
    val artistId: Long,
    @SerializedName("artistUrl")
    val imageUrl: String,
)