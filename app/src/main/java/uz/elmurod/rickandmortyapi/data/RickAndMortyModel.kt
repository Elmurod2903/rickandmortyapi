package uz.elmurod.rickandmortyapi.data

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class RickAndMortyModel(
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val image: String? = null

) : Serializable

@JsonClass(generateAdapter = true)
data class Origins(
    val name: String? = null
):Serializable