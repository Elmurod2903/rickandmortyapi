package uz.elmurod.rickandmortyapi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.mapper.RAMRMMapper
import uz.elmurod.rickandmortyapi.network.port.RickAndMortyPort
import java.io.IOException

private const val START_PAGE_INDEX = 1

class RAMPagingDataSource(
    private val ramPort: RickAndMortyPort,
    private val rmMapper: RAMRMMapper
) : PagingSource<Int, RAM>() {
    override fun getRefreshKey(state: PagingState<Int, RAM>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RAM> {
        val position = params.key ?: START_PAGE_INDEX
        return try {
            val response = ramPort.rickAndMortyApi(position)
            val news = rmMapper.mapFromResponseList(response.results)

            LoadResult.Page(
                data = news,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1,
                nextKey = if (news.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}