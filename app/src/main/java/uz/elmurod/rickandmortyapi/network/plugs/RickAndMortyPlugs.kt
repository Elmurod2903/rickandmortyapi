package uz.elmurod.rickandmortyapi.network.plugs

import uz.elmurod.rickandmortyapi.data.BaseResponse
import uz.elmurod.rickandmortyapi.data.RickAndMortyModel
import uz.elmurod.rickandmortyapi.network.api.RickAndMortyApi
import uz.elmurod.rickandmortyapi.network.port.RickAndMortyPort
import javax.inject.Inject

class RickAndMortyPlugs @Inject constructor(private val service: RickAndMortyApi) :
    RickAndMortyPort {

    override suspend fun rickAndMortyApi(pageNumber: Int): BaseResponse<RickAndMortyModel> {
        return service.rickAndMortyApi(pageNumber)
    }

}