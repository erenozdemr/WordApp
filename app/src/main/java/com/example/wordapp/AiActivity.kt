package com.example.wordapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wordapp.databinding.ActivityAiBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class AiActivity : AppCompatActivity() {
    lateinit var binding: ActivityAiBinding
    val client=OkHttpClient()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityAiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.setOnClickListener {
            var ques=binding.etAiQuestion.text.toString()
            if(ques!=null){
                binding.etAiQuestion.text=null
                getresponse(ques){response->
                    runOnUiThread {

                        binding.tvAiAnswer.setText(response)
                    }

                }
            }
        }




    }

    fun getresponse(question: String, callback: (String) -> Any){

        try{
            val apikey=""
            val url="https://api.openai.com/v1/completions"
            val requestBody="""
            {
            "model": "text-davinci-003",
  "prompt": "$question",
  "max_tokens": 500,
  "temperature": 0
            }
        """.trimIndent()

            val request=Request.Builder().url(url)
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","Bearer $apikey")
                .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
                .build()
            client.newCall(request).enqueue(object:Callback{
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG)
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val body=response.body?.string()

                    println(body)
                    try{
                        val jsonObject= JSONObject(body)
                        val jsonArray=jsonObject.getJSONArray("choices")
                        val text=jsonArray.getJSONObject(0).getString("text")
                        callback(text)
                    }catch (e:Exception){
                        e.printStackTrace()
                        Toast.makeText(applicationContext,"An error occurred",Toast.LENGTH_LONG).show()
                    }

                }
            })
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()

        }


    }






    /*

   */
}