package uz.elmurod.rickandmortyapi.network.port

import uz.elmurod.rickandmortyapi.data.BaseResponse
import uz.elmurod.rickandmortyapi.data.RickAndMortyModel

interface RickAndMortyPort {

    suspend fun rickAndMortyApi(
        pageNumber: Int
    ): BaseResponse<RickAndMortyModel>
}