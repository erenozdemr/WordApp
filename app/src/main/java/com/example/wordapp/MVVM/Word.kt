package com.example.wordapp.MVVM

import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("word")
    var word:String,
    @SerializedName("meanings")
    var meanings:ArrayList<Meanings>,
    @SerializedName("phonetics")
    var phonetic:ArrayList<Phonetics>,
    var id:String?=null,
    var note:String?=null

) {
}