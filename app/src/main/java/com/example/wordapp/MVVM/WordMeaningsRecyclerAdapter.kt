package com.example.wordapp.MVVM

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordapp.R

class WordMeaningsRecyclerAdapter(var meanings:ArrayList<Meanings>):RecyclerView.Adapter<WordMeaningsRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val tvKind=itemview.findViewById<TextView>(R.id.tvKind)
        val tvKindExp=itemview.findViewById<TextView>(R.id.tvKindExp)
        val tvExample=itemview.findViewById<TextView>(R.id.tvExample)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate=LayoutInflater.from(parent.context).inflate(R.layout.word_detail_row,parent,false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return meanings.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvKind.setText(meanings.get(position).partOfSpeech)
        holder.tvKindExp.setText((meanings.get(position).definitions!!.get(0).definition))
        holder.tvExample.setText((meanings.get(position).definitions!!.get(0).definition))


    }
    fun refreshMeanings(newMeanings:ArrayList<Meanings>){

        meanings.clear()
        meanings.addAll(newMeanings)
        notifyDataSetChanged()
    }
}