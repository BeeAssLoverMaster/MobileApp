package shkonda.artschools.data.data_source.schools.entity

import com.google.gson.annotations.SerializedName

data class SchoolsDto(
    @SerializedName("schools")
    val schoolList: List<SchoolDto>
)

data class SchoolDto(
    @SerializedName("schoolId")
    val id: Long,
    @SerializedName("schoolName")
    val schoolName: String,
    @SerializedName("artCategoryId")
    val artCategoryId: Long,
    @SerializedName("artCategoryName")
    val artCategoryName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("schoolImageName")
    val schoolImageName:String,
    @SerializedName("programs")
    val programList: List<ProgramDto>
)

data class ProgramDto(
    @SerializedName("programId")
    val programId: Long,
    @SerializedName("programName")
    val programName: String,
)