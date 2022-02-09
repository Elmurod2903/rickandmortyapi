    package uz.elmurod.rickandmortyapi.network.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("pref_data")

class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    private val isNetworkOkKey = booleanPreferencesKey("is_network_ok")


    suspend fun setNetworkStatus(bool: Boolean) {
        settingsDataStore.edit { data ->
            data[isNetworkOkKey] = bool
        }
    }

    val networkStatus: Flow<Boolean> = settingsDataStore.data.map { data ->
        data[isNetworkOkKey] ?: false
    }

}
