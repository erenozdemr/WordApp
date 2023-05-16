package com.example.wordapp.MVVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.ArrayList

class WordListViewModel:ViewModel() {
    val words=MutableLiveData<List<Word>>()
    val wordsErrors=MutableLiveData<Boolean>()
    val wordsLoading=MutableLiveData<Boolean>()


    fun refreshData(){
        val w1=Word("dsaad", ArrayList(), ArrayList())
        val w2=Word("djklljh", ArrayList(), ArrayList())
        val w3=Word("duruyru", ArrayList(), ArrayList())
        val wordList= arrayListOf<Word>(w1,w2,w3)
        words.value=wordList
        wordsErrors.value=false
        wordsLoading.value=false
    }
}