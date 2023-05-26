package com.example.wordapp.MVVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.wordapp.services.WordDatabase
import kotlinx.coroutines.launch

class SwipeWordViewModel(application: Application):BaseViewModel(application) {
    var words=MutableLiveData<ArrayList<Word>?>()

    fun getWords(){
        launch {
            val wordList= WordDatabase(getApplication()).wordDao().getAllWords()
            if(wordList!=null){
                words.value=(wordList as ArrayList<Word>)

            }

        }
    }


}