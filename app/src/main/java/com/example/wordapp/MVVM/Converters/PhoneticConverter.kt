package com.example.wordapp.MVVM.Converters

import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Phonetics

class PhoneticConverter {
    @TypeConverter
    fun textFromPhonetics(phonetic: Phonetics):String{
        return phonetic.text
    }

    @TypeConverter
    fun newPhonetic(text:String): Phonetics {
        return Phonetics(text,text)
    }


}