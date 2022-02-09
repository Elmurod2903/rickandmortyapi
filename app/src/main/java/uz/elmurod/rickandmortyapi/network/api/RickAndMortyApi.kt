package uz.elmurod.rickandmortyapi.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.elmurod.rickandmortyapi.data.BaseResponse
import uz.elmurod.rickandmortyapi.data.RickAndMortyModel
import uz.elmurod.rickandmortyapi.util.Endpoints

interface RickAndMortyApi {

    @GET(Endpoints.character)
    suspend fun rickAndMortyApi(
        @Query("page")
        pageNumber: Int = 1
    ): BaseResponse<RickAndMortyModel>
}