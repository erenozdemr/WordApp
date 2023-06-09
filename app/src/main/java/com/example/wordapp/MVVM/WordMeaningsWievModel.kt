
package com.example.wordapp.MVVM

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.wordapp.services.RequestManager
import com.example.wordapp.services.WordDatabase
import com.example.wordapp.services.onFetchDataListener
import kotlinx.coroutines.launch
import java.util.UUID


class WordMeaningsWievModel(application: Application):BaseViewModel(application) {
    val meanings= MutableLiveData<Word?>()
    val meaningsErrors= MutableLiveData<Boolean>()
    val meaningsLoading= MutableLiveData<Boolean>()
    val meaningsSaved=MutableLiveData<Boolean>()




    fun inTheBeginning(){
        meaningsErrors.value=true
        meaningsLoading.value=false
    }
    fun getWord(url:String,context:Context){
        meaningsErrors.value=false
        meaningsLoading.value=true
        meanings.value=null
        var manager=RequestManager(context)
        manager.getWordMeaning(object:onFetchDataListener{
            override fun onFetchdata(apiResponce: Word?, message: String) {

                if(apiResponce!=null){
                    meaningsLoading.value=false
                    meanings.value=apiResponce
                    isinDatabase()
                }else{
                    meaningsLoading.value=false
                    meaningsErrors.value=true
                }




            }

            override fun onError(message: String) {
                meaningsLoading.value=false
                meaningsErrors.value=true
            }

        },url)


    }
    fun saveWord(){
        meanings.value.let {
            launch {

                var dao=WordDatabase.invoke(getApplication()).wordDao()
                isinDatabase()


                if (meaningsSaved.value==false){
                    val uuid=UUID.randomUUID().toString()
                    meanings.value!!.id=uuid
                    dao.insertAll(meanings.value!!)
                    isinDatabase()
                }else{
                    unSaveWord()
                }




            }
        }

    }
    fun unSaveWord(){
        meanings.value.let {
            launch {

                var dao=WordDatabase.invoke(getApplication()).wordDao()



                dao.deleteWordWithID(meanings.value!!.id)
                isinDatabase()



            }
        }

    }

    fun getWordWithID(id:String){
        meaningsLoading.value=true
        launch {

             var dao=WordDatabase.invoke(getApplication()).wordDao()
            meaningsLoading.value=false

             meanings.value=dao.getWord(id)
            isinDatabase()

        }
    }
    fun isinDatabase(){
        if(meanings.value!=null){
            launch {

                var dao=WordDatabase.invoke(getApplication()).wordDao()
                var tempWord=dao.toControlInDatabase(meanings.value!!.word)
                if(tempWord!=null){
                    meanings.value=tempWord!!
                }
                meaningsSaved.value = tempWord!=null

            }
        }

    }



}


