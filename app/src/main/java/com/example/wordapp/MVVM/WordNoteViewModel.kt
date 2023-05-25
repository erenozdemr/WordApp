package com.example.wordapp.MVVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.wordapp.services.WordDatabase
import kotlinx.coroutines.launch

class WordNoteViewModel(application: Application):BaseViewModel(application) {
    var note=MutableLiveData<String?>()
    fun getWordnoteWithID(id:String){
        launch {

            var dao= WordDatabase.invoke(getApplication()).wordDao()
            var temp=dao.getWord(id).note
            if(temp!=null){
                note.value=temp
            }



        }
    }
    fun updateWordnoteWithID(id:String,newNote:String){
        launch {

            var dao= WordDatabase.invoke(getApplication()).wordDao()
            dao.updateNote(newNote,id)



        }
    }
}