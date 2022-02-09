package uz.elmurod.rickandmortyapi.mapper

import uz.elmurod.rickandmortyapi.base.ResponseModelMapper
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.data.RickAndMortyModel
import javax.inject.Inject

class RAMRMMapper @Inject constructor(
) : ResponseModelMapper<RickAndMortyModel, RAM> {
    override fun mapFromResponse(response: RickAndMortyModel): RAM = RAM(
        id = response.id,
        image = response.image,
        status = response.status,
        name = response.name
    )

}