package com.example.wordapp.MVVM.Converters

import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Meanings
import com.example.wordapp.MVVM.Phonetics
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.typeOf

class MeaningArrayConverter {
    @TypeConverter
    fun fromMeanings(meanings: Meanings): ArrayList<Meanings> {
        val type = object : TypeToken<ArrayList<Meanings>>() {}.type
        val list = ArrayList<Meanings>()
        list.add(meanings)
        return Gson().fromJson<ArrayList<Meanings>>(Gson().toJson(list), type)
    }

    @TypeConverter
    fun fromArraylist(list:ArrayList<Meanings>): Meanings {
        return list.get(0)
    }
}