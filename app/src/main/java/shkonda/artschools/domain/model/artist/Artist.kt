package shkonda.artschools.domain.model.artist

import com.google.gson.annotations.SerializedName

data class Artist(
    val id: Long,
    val artistName: String,
    val imageUrl: String,
    val articleId: Long
)