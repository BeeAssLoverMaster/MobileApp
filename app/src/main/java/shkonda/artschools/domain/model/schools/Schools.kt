package shkonda.artschools.domain.model.schools

data class Schools(
    val schoolList: List<School>
)

data class School(
    val id: Long,
    val schoolName: String,
    val artCategoryId: Long,
    val artCategoryName: String,
    val description: String,
    val city: String,
    val street: String,
    val schoolImageName:String,
    val programList: List<Program>
)

data class Program(
    val programId: Long,
    val programName: String,
)