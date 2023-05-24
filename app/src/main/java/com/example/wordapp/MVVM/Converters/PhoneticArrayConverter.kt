package com.example.wordapp.MVVM.Converters

import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Meanings
import com.example.wordapp.MVVM.Phonetics
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.converter.gson.GsonConverterFactory

class PhoneticArrayConverter {
    @TypeConverter
    fun fromPhonetic(phohonetic:Phonetics):ArrayList<Phonetics>{
        val type = object : TypeToken<ArrayList<Phonetics>>() {}.type
        val list = ArrayList<Phonetics>()
        list.add(phohonetic)
        return Gson().fromJson<ArrayList<Phonetics>>(Gson().toJson(list), type)
    }
    @TypeConverter
    fun fromArraylist(list:ArrayList<Phonetics>):Phonetics{
        return list.get(0)
    }
}