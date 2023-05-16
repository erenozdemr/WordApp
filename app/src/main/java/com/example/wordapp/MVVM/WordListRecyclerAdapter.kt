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
        holder.tvWord.text=wordList.get(position).word
        //meaning parts will be added
    }
    fun refreshWordlist(newWordlist:ArrayList<Word>){
        wordList.clear()
        wordList.addAll(newWordlist)
        notifyDataSetChanged()
    }
}