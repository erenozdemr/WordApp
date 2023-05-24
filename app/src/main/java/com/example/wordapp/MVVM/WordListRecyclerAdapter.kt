package com.example.wordapp.MVVM

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordapp.R

class WordListRecyclerAdapter(var wordList:ArrayList<Word>):RecyclerView.Adapter<WordListRecyclerAdapter.WordListViewHolder>() {
    class WordListViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        var tvWord=itemView.findViewById<TextView>(R.id.tvWord)
        var tvMeaning=itemview.findViewById<TextView>(R.id.tvMeaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        var inflate=LayoutInflater.from(parent.context)
        return  WordListViewHolder(inflate.inflate(R.layout.word_list_row,parent,false))
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        if(wordList.size!=0){
            holder.tvWord.text=wordList.get(position).word
            println(wordList.get(position).meanings.get(0).partOfSpeech)
            println(wordList.get(position).phonetic.get(0).text)
            println(wordList.get(position).meanings.get(0).definitions[0].example)
            holder.tvMeaning.text=wordList.get(position).meanings.get(0).definitions.get(0).definition
        }

    }
    fun refreshWordlist(newWordlist:ArrayList<Word>){
        wordList.clear()
        wordList.addAll(newWordlist)
        notifyDataSetChanged()
    }
}