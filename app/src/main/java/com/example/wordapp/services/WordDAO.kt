package com.example.wordapp.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wordapp.MVVM.Word

@Dao
interface WordDAO {

    @Insert
    suspend fun insertAll(vararg word : Word):List<Long>

    @Query("SELECT * FROM word")
    suspend fun getAllWords():List<Word>

    @Query("SELECT * FROM word WHERE id = :wordID")
    suspend fun getWord(wordID:String):Word
    @Query("SELECT * FROM word WHERE word = :word")
    suspend fun toControlInDatabase(word:String):Word?

    @Query("DELETE FROM word")
    suspend fun deleteAllWords()
    @Query("DELETE FROM word WHERE id = :wordID")
    suspend fun deleteWordWithID(wordID: String)

    @Query("UPDATE word SET note= :newNote WHERE id = :WordID")
    suspend fun updateNote(newNote:String, WordID: String)
}