
package com.example.wordapp.MVVM

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wordapp.services.RequestManager
import com.example.wordapp.services.WordDAO
import com.example.wordapp.services.WordDatabase
import com.example.wordapp.services.onFetchDataListener
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.coroutineContext


class WordMeaningsWievModel(application: Application):BaseViewModel(application) {
    val meanings= MutableLiveData<Word>()
    val meaningsErrors= MutableLiveData<Boolean>()
    val meaningsLoading= MutableLiveData<Boolean>()




    fun refreshData(){

        meaningsErrors.value=false
        meaningsLoading.value=false
    }
    fun getWord(url:String,context:Context){
        meaningsLoading.value=true
        var manager=RequestManager(context)
        println("getword e girildi")
        manager.getWordMeaning(object:onFetchDataListener{
            override fun onFetchdata(apiResponce: Word, message: String) {
                println("onfetchdata ya girildi")

                meaningsErrors.value=false
                meaningsLoading.value=false
                meanings.value=apiResponce



            }

            override fun onError(message: String) {
                println("on error a girildi")

                meaningsErrors.value=true
            }

        },url)


    }
    fun saveWord(){
        launch {
            meanings.value.let {
                var dao=WordDatabase.invoke(getApplication()).wordDao()

                val uuid=UUID.randomUUID().toString()
                meanings.value!!.id=uuid
                dao.insertAll(meanings.value!!)

            }

        }
    }



}


