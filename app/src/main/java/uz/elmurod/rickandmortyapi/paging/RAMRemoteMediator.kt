package uz.elmurod.rickandmortyapi.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.data.RemoteKeys
import uz.elmurod.rickandmortyapi.database.RAMDatabase
import uz.elmurod.rickandmortyapi.mapper.RAMRMMapper
import uz.elmurod.rickandmortyapi.network.port.RickAndMortyPort
import java.io.IOException

@ExperimentalPagingApi
class RAMRemoteMediator(
    private val db: RAMDatabase,
    private val ramPort: RickAndMortyPort,
    private val rmMapper: RAMRMMapper

) : RemoteMediator<Int, RAM>() {
    private val STARTING_PAGE_INDEX = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, RAM>): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)

        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        try {
            val response = ramPort.rickAndMortyApi(page)
            val responseRAM = rmMapper.mapFromResponseList(response.results)

            val endOfList = responseRAM.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeyDao().clearAll()
                    db.getRAMDao().clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfList) null else page + 1
                val keys = responseRAM.map {
                    it.id?.let { it1 -> RemoteKeys(it1, prevKey, nextKey) }
                }
                db.remoteKeyDao().insertRemote(keys)
                db.getRAMDao().insert(responseRAM)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfList)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, RAM>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?: MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, RAM>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { dog -> dog.id?.let { db.remoteKeyDao().getRemoteKeys(it) } }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, RAM>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages
                .lastOrNull { it.data.isNotEmpty() }
                ?.data?.lastOrNull()
                ?.let { dog -> dog.id?.let { db.remoteKeyDao().getRemoteKeys(it) } }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, RAM>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { repId ->
                    db.remoteKeyDao().getRemoteKeys(repId)
                }
            }
        }
    }

}