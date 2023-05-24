package com.example.wordapp.MVVM.Converters

import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Definitions
import com.example.wordapp.MVVM.Meanings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DefinitionsArrayConverter {
    @TypeConverter
    fun fromDefinitions(defs: Definitions): ArrayList<Definitions> {
        val type = object : TypeToken<ArrayList<Definitions>>() {}.type
        val list = ArrayList<Definitions>()
        list.add(defs)
        return Gson().fromJson<ArrayList<Definitions>>(Gson().toJson(list), type)
    }

    @TypeConverter
    fun fromArraylist(list:ArrayList<Definitions>): Definitions {
        return list.get(0)
    }
}