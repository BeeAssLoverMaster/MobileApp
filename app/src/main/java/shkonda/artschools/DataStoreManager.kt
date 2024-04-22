package shkonda.artschools

import android.content.Context
import android.service.autofill.UserData
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import shkonda.artschools.domain.model.user.UserProfile


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(val context: Context) {

    suspend fun saveUserData(userData: UserProfile) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("username")] = userData.userName
            pref[stringPreferencesKey("profilePicture")] = userData.profilePictureUrl
        }
    }

    fun getUserData() = context.dataStore.data.map { pref ->
        return@map UserProfile(
            pref[stringPreferencesKey("username")] ?: "null",
            pref[stringPreferencesKey("profilePicture")] ?: "null"
        )
    }
}