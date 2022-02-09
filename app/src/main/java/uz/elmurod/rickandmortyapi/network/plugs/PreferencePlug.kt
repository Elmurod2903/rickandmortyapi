package uz.elmurod.rickandmortyapi.network.plugs

import kotlinx.coroutines.flow.Flow
import uz.elmurod.rickandmortyapi.network.datastore.DataStoreManager
import uz.elmurod.rickandmortyapi.network.port.PreferencePort
import javax.inject.Inject

class PreferencePlug @Inject constructor(private val dataStoreManager: DataStoreManager) :
    PreferencePort {
    override suspend fun setNetworkStatus(boolNet: Boolean) {
        dataStoreManager.setNetworkStatus(boolNet)
    }

    override fun networkStatus(): Flow<Boolean> {
        return dataStoreManager.networkStatus
    }
}