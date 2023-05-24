package com.example.wordapp.MVVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wordapp.services.WordDatabase
import kotlinx.coroutines.launch


class WordListViewModel(application: Application):BaseViewModel(application ) {
    val words=MutableLiveData<ArrayList<Word>>()
    val wordsErrors=MutableLiveData<Boolean>()
    val wordsLoading=MutableLiveData<Boolean>()


    fun getWords(){
        launch {
            val wordList=WordDatabase(getApplication()).wordDao().getAllWords()
            if(wordList!=null){
                showWords(wordList as ArrayList<Word>)

            }

        }
    }
    fun showWords(wordlist:ArrayList<Word>){
        words.value=wordlist
        wordsErrors.value=false
        wordsLoading.value=false
    }
}