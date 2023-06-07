package com.example.wordapp.services

import com.example.wordapp.MVVM.Word

interface onFetchDataListener {
    fun onFetchdata(apiResponce: Word?, message:String)
    fun onError(message: String)
}