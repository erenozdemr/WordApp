package com.example.wordapp.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wordapp.MVVM.Word

@Database(entities = arrayOf(Word::class), version = 1)
abstract class WordDatabase:RoomDatabase() {
    abstract fun wordDao():WordDAO

    companion object{
        @Volatile private var instance:WordDatabase?=null
        private val lock =Any()
        operator fun invoke(context: Context)= instance?: synchronized(lock){
            instance?: createWordDatabase(context).also {
                instance=it
            }
        }

        private fun createWordDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,WordDatabase::class.java,"worddatabase"
        ).build()
    }



}