package kz.dragau.larek.models.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    companion object {
        val gson = Gson()

        @TypeConverter
        fun fromString(value: String): ArrayList<String> {
            val listType = object : TypeToken<ArrayList<String>>() {

            }.type

            return gson.fromJson<ArrayList<String>>(value, listType)
        }

        @TypeConverter
        fun fromArrayList(list: ArrayList<String>): String {

            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toDate(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun toLong(value: Date?): Long? {
            return (value?.time)
        }
    }
}