package shkonda.artschools.domain.model.artist

import com.google.gson.annotations.SerializedName

data class SimpleArtists(
    val artistList: List<SimpleArtist>
)
data class SimpleArtist(
    val quizId: Long,
    val artistId: Long,
    val imageUrl: String,
)