package com.example.wordapp.MVVM

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.wordapp.services.WordDatabase
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuizViewModel(application: Application):BaseViewModel(application) {
    var questions=MutableLiveData<ArrayList<Question>?>()
    var quesError=MutableLiveData<Boolean>()
    fun getTenQuestion(){
        launch {
            val wordList= WordDatabase(getApplication()).wordDao().getAllWords()
            if(wordList!=null&&wordList.size>=10){
                var tenQues=ArrayList<Question>()
                var words=ArrayList<String>()

                var i=0
                while (i<10){
                    var tempanswers=ArrayList<String>()
                    var temp=wordList.get(Random.nextInt(wordList.size))
                    if(words.contains(temp.word)){

                    }else{
                        words.add(temp.word)
                        tempanswers.add(temp.word)
                        var j=1
                        while(j<4){
                            var tempAnswer=wordList.get(Random.nextInt(wordList.size)).word
                            if(tempanswers.contains(tempAnswer)){

                            }else{
                                j++
                                tempanswers.add(tempAnswer)
                            }

                        }
                        j=0
                        var answers=ArrayList<String>()
                        while (j<4){
                            var str=tempanswers.get(Random.nextInt(tempanswers.size))
                            tempanswers.remove(str)
                            answers.add(str)
                            j++
                        }
                        var ques=Question(temp.meanings[0].definitions[0].definition,answers[0],answers[1],answers[2],answers[3],temp.word)
                        tenQues.add(ques)
                        i++
                    }

                }
                questions.value=tenQues


            }
            else{
                quesError.value=true
                Toast.makeText(getApplication(),"You have to save 10 words at least",Toast.LENGTH_LONG).show()
            }
        }
    }
}