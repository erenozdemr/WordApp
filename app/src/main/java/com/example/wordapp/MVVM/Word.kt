package com.example.wordapp.MVVM

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class Word(
    @ColumnInfo(name = "word")
    @SerializedName("word")
    var word:String,
    @ColumnInfo(name = "meanings")
    @SerializedName("meanings")
    var meanings:ArrayList<Meanings>,
    @ColumnInfo(name = "phonetics")
    @SerializedName("phonetics")
    var phonetic:ArrayList<Phonetics>,
    var note:String?=null

) {
    @PrimaryKey(autoGenerate = false)
    var id=""
}