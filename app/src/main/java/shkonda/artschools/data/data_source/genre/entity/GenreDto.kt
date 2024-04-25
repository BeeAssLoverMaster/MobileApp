package shkonda.artschools.data.data_source.genre.entity

import com.google.gson.annotations.SerializedName

data class GenresDto (
    @SerializedName("genres")
    val categories: ArrayList<GenreDto>
)

data class GenreDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("genreName")
    val genreName: String,

//    @SerializedName("imageUrl")
//    val genreImage: String
)
