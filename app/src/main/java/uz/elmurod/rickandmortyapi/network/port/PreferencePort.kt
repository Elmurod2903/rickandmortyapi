package uz.elmurod.rickandmortyapi.network.port

import kotlinx.coroutines.flow.Flow

interface PreferencePort {

    suspend fun setNetworkStatus(boolNet: Boolean)

    fun networkStatus(): Flow<Boolean>

}
