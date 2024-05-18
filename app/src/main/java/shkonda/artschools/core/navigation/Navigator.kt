package shkonda.artschools.core.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.MutableStateFlow

object Navigator {
    var destination: MutableStateFlow<String> = MutableStateFlow("")
    var navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}

    fun navigate(destination: String, navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
        this.destination.value = destination
        this.navOptionsBuilder = navOptionsBuilder
    }

    fun resetDestination() {
        this.destination.value = ""
    }
}