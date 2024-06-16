package shkonda.artschools.presentation.school_page.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import shkonda.artschools.domain.model.schools.School
import shkonda.artschools.presentation.school_page.ArtSchoolViewModel

@Composable
fun getAllSchools(viewModel: ArtSchoolViewModel): List<School> {
    val allSchoolsState by viewModel.allSchoolState.collectAsState()
    return when (val state = allSchoolsState) {
        is SchoolState.Success -> state.schoolData.schoolList
        is SchoolState.Loading -> emptyList()
        is SchoolState.Error -> emptyList()
    }
}