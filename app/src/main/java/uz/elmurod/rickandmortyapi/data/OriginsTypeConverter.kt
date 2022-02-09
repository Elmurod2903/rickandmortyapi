package uz.elmurod.rickandmortyapi.data

import androidx.room.TypeConverter
import org.json.JSONException
import org.json.JSONObject

class OriginsTypeConverter {

    @TypeConverter
    fun fromOrigins(origins: Origins): String {
        return JSONObject().apply {
            put("name", origins.name)
        }.toString()

    }

    @TypeConverter
    fun toOrigins(origins: String): Origins {
        try {
            val json = JSONObject(origins)
            Origins(json.getString("name"))
        } catch (e: JSONException) {
            e.message
        }
        return Origins()
    }
//    @TypeConverter
//    fun ConvertSource(origins: Origins?): String? {
//        return origins?.toString()
//    }
//
//    @TypeConverter
//    fun ConvertSource(origins: String?): Origins? {
//        return origins?.let { Origins(it) }
//    }
}