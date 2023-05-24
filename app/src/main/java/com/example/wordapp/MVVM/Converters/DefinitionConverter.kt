package com.example.wordapp.MVVM.Converters

import androidx.room.TypeConverter
import com.example.wordapp.MVVM.Definitions

class DefinitionConverter {
    @TypeConverter
    fun definitionFromDefinitions(definitions: Definitions):String{
        return definitions.definition
    }

    @TypeConverter
    fun newDefinition(definition:String): Definitions {
        return Definitions(definition,definition)
    }
}