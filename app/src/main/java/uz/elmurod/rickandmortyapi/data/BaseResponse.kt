package uz.elmurod.rickandmortyapi.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    val results: List<T>
)