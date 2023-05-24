
package com.example.wordapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordapp.MVVM.Word
import com.example.wordapp.MVVM.WordListRecyclerAdapter
import com.example.wordapp.MVVM.WordListViewModel
import com.example.wordapp.MVVM.WordMeaningsWievModel
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.coroutines.launch


class ListWordsFragment : Fragment() {
    lateinit var viewModel:WordListViewModel
    var adapter=WordListRecyclerAdapter(arrayListOf())
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_words, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(WordListViewModel::class.java)
        recyclerView=view.findViewById(R.id.wordListRecycler)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=adapter


        observeLiveData()
        viewModel.getWords()


    }
    fun observeLiveData(){

        viewModel.words.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter.refreshWordlist(it)

            }
        })
        viewModel.wordsErrors.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    Toast.makeText(context,"there is and error occured", Toast.LENGTH_LONG).show()
                    //buraya daha sonra hata mesajı ekelenbilir
                }
            }
        })
        viewModel.wordsLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(context,"Word is loading", Toast.LENGTH_LONG).show()// daha sonra progress bar eklenebilir
            }
        })
    }



}