package com.example.wordapp.services

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.example.wordapp.MVVM.Word
import io.reactivex.Single
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class RequestManager(var context: Context) {

    private val BASE_URL="https://api.dictionaryapi.dev/api/v2/"
    private val api= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()


    fun getWordMeaning(listener:onFetchDataListener,wordInput:String)  {
        var callDictionary=api.create(DictionaryAPI::class.java)
        var word : Call<ArrayList<Word>> = callDictionary.getWord(wordInput)
        println("get word meaning e girildi")
        try {
            word.enqueue( object: Callback<ArrayList<Word>>{
                override fun onResponse(
                    call: Call<ArrayList<Word>>,
                    response: Response<ArrayList<Word>>
                ) {
                    if(!response.isSuccessful){
                        Toast.makeText(context,"An error occured",Toast.LENGTH_LONG).show()
                        return
                    }
                    listener.onFetchdata(response.body()!!.get(0),response.message())

                }

                override fun onFailure(call: Call<ArrayList<Word>>, t: Throwable) {
                    t.printStackTrace()
                    listener.onError("Request Failed")

                }

            })



        }catch (e:java.lang.Exception){
            e.printStackTrace()
            Toast.makeText(context,"An error occured",Toast.LENGTH_LONG).show()
        }
    }

    interface DictionaryAPI {
        @GET("entries/en/{url}")
        fun getWord(@Path("url") url:String): Call<ArrayList<Word>>
    }
}
