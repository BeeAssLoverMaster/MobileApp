package shkonda.artschools.presentation.school_page.states

import shkonda.artschools.domain.model.schools.Schools

sealed interface SchoolState {
    object Loading : SchoolState
    data class Success(val schoolData: Schools) : SchoolState
    data class Error(val errorMessage: String) : SchoolState
}