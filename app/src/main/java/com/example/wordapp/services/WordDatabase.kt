package com.example.wordapp.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wordapp.MVVM.*
import com.example.wordapp.MVVM.Converters.*

@Database(entities = arrayOf(Word::class), version = 2)
@TypeConverters(
    PhoneticConverter::class, MeaningConverter::class,
    DefinitionConverter::class, DefinitionConverter2::class,
    PhoneticArrayConverter::class,MeaningArrayConverter::class
    ,JsonConverter::class,DefinitionsArrayConverter::class,JsonmeaningConverter::class)
abstract class WordDatabase:RoomDatabase() {
    abstract fun wordDao():WordDAO

    companion object{
        @Volatile private var INSTANCE:WordDatabase?=null

        operator fun invoke(context: Context):WordDatabase{
           var  instance= INSTANCE
            if(instance!=null){
                   return instance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,WordDatabase::class.java,"WordDatabase.db").build()
                INSTANCE=instance
                return instance
            }


        }

    }




}