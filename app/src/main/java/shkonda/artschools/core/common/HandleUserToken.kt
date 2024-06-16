package shkonda.artschools.core.common

import android.content.SharedPreferences

fun SharedPreferences.Editor.storeToken(token: String) {
    putString("token", token)
    apply()
}

fun SharedPreferences.getToken(): String? {
    return getString("token", "")
}

fun SharedPreferences.Editor.removeToken() {
    remove("token")
    apply()
}

fun SharedPreferences.Editor.storeCategoryId(categoryId: Int) {
    putInt("categoryId", categoryId)
    apply()
}

fun SharedPreferences.getCategoryId(): Int {
    return getInt("token", -1)
}

fun SharedPreferences.Editor.storeCorrectAnswersCount(token: String, correctAnswersCount: Int) {
    putInt("correct_answers_count_$token", correctAnswersCount)
    apply()
}

fun SharedPreferences.getCorrectAnswersCount(token: String): Int {
    return getInt("correct_answers_count_$token", 0)
}
