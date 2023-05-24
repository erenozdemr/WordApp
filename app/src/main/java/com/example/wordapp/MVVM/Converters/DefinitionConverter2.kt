package com.example.wordapp.MVVM.Converters

import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Definitions
import com.example.wordapp.MVVM.Meanings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DefinitionConverter2 {
    @TypeConverter
    fun definitionsFromMeanings(meanings: Meanings):ArrayList<Definitions>{
        return meanings.definitions
    }
    @TypeConverter
    fun newMeaning(list:ArrayList<Definitions>): Meanings {
        return Meanings("",list)
    }
}