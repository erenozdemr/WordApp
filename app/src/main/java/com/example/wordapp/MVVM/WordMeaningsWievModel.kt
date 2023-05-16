package com.example.wordapp.MVVM

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wordapp.services.RequestManager
import com.example.wordapp.services.onFetchDataListener
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.coroutineContext


class WordMeaningsWievModel:ViewModel() {
    val meanings= MutableLiveData<Word>()
    val meaningsErrors= MutableLiveData<Boolean>()
    val meaningsLoading= MutableLiveData<Boolean>()


    private val disposable=CompositeDisposable()



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

        /*disposable.add(
            RequestManager().getWordMeaning(onFetchDataListener(),url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribeWith(object: DisposableSingleObserver<ArrayList<Word>>(){
                    override fun onSuccess(t: ArrayList<Word>) {
                        meanings.value=t
                        meaningsErrors.value=false
                        meaningsLoading.value=false

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        //error verme kısmı eklenebilir

                    }

                })

        )*/
    }

}


