package shkonda.artschools.presentation.school_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.domain.usecase.schools.GetSchoolUseCase
import shkonda.artschools.presentation.school_page.states.SchoolState
import javax.inject.Inject

@HiltViewModel
class ArtSchoolViewModel @Inject constructor(
    private val getSchoolUseCase: GetSchoolUseCase
) : ViewModel(){
    private val _allSchoolsState = MutableStateFlow<SchoolState>(SchoolState.Loading)
    val allSchoolState = _allSchoolsState.asStateFlow()

    init {
        getAllSchools()
    }

    private fun getAllSchools() = viewModelScope.launch(Dispatchers.IO) {
        getSchoolUseCase().collect() {response ->
            when (response) {
                is Response.Loading -> {
                    _allSchoolsState.value = SchoolState.Loading
                }

                is Response.Success -> {
                    _allSchoolsState.value =
                        SchoolState.Success(schoolData = response.data)
                }

                is Response.Error -> {
                    _allSchoolsState.value =
                        SchoolState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}