package shkonda.artschools.core.common

import android.util.Patterns

object EmailController {
    fun isEmailType(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}