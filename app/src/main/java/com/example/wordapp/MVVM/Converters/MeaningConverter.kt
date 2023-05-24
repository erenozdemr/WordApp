package com.example.wordapp.MVVM.Converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Definitions
import com.example.wordapp.MVVM.Meanings
import com.google.gson.JsonParser
@ProvidedTypeConverter
class MeaningConverter(jsonParser: JsonParser) {
    @TypeConverter
    fun partOfSpeechFromMeanings(meanings: Meanings):String{
        return meanings.partOfSpeech
    }

    @TypeConverter
    fun newMeaning(partOfSpeech:String): Meanings {
        return Meanings(partOfSpeech,ArrayList<Definitions>())
    }

}