package com.example.wordapp.MVVM.Converters

import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Meanings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonmeaningConverter {
    @TypeConverter
    fun fromStringToMeanings(value: String): ArrayList<Meanings> {
        return Gson().fromJson(value,object :
            TypeToken<ArrayList<Meanings>>(){}.type)?: arrayListOf<Meanings>()
    }

    @TypeConverter
    fun fromArrayListToString(list: ArrayList<Meanings>): String {

        return Gson().toJson(list,object : TypeToken<ArrayList<Meanings>>(){}.type)?:"[]"
    }
}