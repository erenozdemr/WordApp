package com.example.wordapp.MVVM

import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.wordapp.R
import java.util.Locale

class WordMeaningsRecyclerAdapter(var meanings:ArrayList<Meanings>):RecyclerView.Adapter<WordMeaningsRecyclerAdapter.ViewHolder>(),TextToSpeech.OnInitListener {

   private var tts:TextToSpeech?=null
    class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val tvKind=itemview.findViewById<TextView>(R.id.tvKind)
        val tvKindExp=itemview.findViewById<TextView>(R.id.tvKindExp)
        val tvExample=itemview.findViewById<TextView>(R.id.tvExample)
        val tvConstant=itemview.findViewById<TextView>(R.id.tvExampleConstant)
        val btnSpeak=itemview.findViewById<ImageButton>(R.id.btnSpeak)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        tts=TextToSpeech(parent.context,this)
        val inflate=LayoutInflater.from(parent.context).inflate(R.layout.word_detail_row,parent,false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return meanings.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvKind.setText(meanings.get(position).partOfSpeech)

        holder.tvKindExp.setText((meanings.get(position).definitions!!.get(0).definition))
        if(meanings.get(position).definitions!!.get(0).example.isNullOrBlank()){
            holder.tvExample.visibility=View.GONE
            holder.tvConstant.visibility=View.GONE
        }
        holder.tvExample.setText((meanings.get(position).definitions!!.get(0).example))
        holder.btnSpeak.setOnClickListener {
            if(tts!=null){
                var text=(meanings.get(position).definitions!!.get(0).definition)+". ."
                if(!meanings.get(position).definitions!!.get(0).example.isNullOrBlank()){
                    text+="Example: ."+(meanings.get(position).definitions!!.get(0).example)
                }
                tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
            }
        }

    }
    fun refreshMeanings(newMeanings:ArrayList<Meanings>){

        meanings.clear()
        meanings.addAll(newMeanings)
        notifyDataSetChanged()
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts?.setLanguage(Locale.UK)
            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Laguage is not supported")
            }
        }else{
            Log.e("TTS","Initalization failed")
        }
    }
}