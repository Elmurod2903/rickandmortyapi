package uz.elmurod.rickandmortyapi.repository

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.database.RAMDatabase
import uz.elmurod.rickandmortyapi.mapper.RAMRMMapper
import uz.elmurod.rickandmortyapi.network.port.RickAndMortyPort
import uz.elmurod.rickandmortyapi.paging.RAMPagingDataSource
import uz.elmurod.rickandmortyapi.paging.RAMRemoteMediator
import java.lang.Exception
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val ramPort: RickAndMortyPort,
    private val db: RAMDatabase,
    private val rmMapper: RAMRMMapper
) {
    @ExperimentalPagingApi
    fun getRickAndMorty(): Flow<PagingData<RAM>>? {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                    maxSize = 100
                ),
                pagingSourceFactory = {
                    db.getRAMDao().getAllRAM()
//                    RAMPagingDataSource(ramPort, rmMapper)
                }, remoteMediator = RAMRemoteMediator(db, ramPort, rmMapper)
            ).flow
        } catch (e: Exception) {
            null
        }
    }
}