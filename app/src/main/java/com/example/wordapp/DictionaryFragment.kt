
package com.example.wordapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordapp.MVVM.WordMeaningsRecyclerAdapter
import com.example.wordapp.MVVM.WordMeaningsWievModel
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.coroutines.flow.callbackFlow


class DictionaryFragment : Fragment() {
    private lateinit var viewModel: WordMeaningsWievModel
    private var adapter= WordMeaningsRecyclerAdapter(arrayListOf())
    lateinit var recyclerDictionary: RecyclerView
    val args: DictionaryFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        recyclerDictionary=view.findViewById(R.id.dictionaryRecycler)
        recyclerDictionary.layoutManager= LinearLayoutManager(view.context)
        recyclerDictionary.adapter=adapter


        viewModel= ViewModelProvider(this).get(WordMeaningsWievModel::class.java)
        viewModel.refreshData()



        val etSearchBox=view.findViewById<EditText>(R.id.etSearchBox)
        val btnSearch=view.findViewById<Button>(R.id.btnSearch)
        val btnNote=view.findViewById<ImageButton>(R.id.btnNote)


        if(arguments?.isEmpty == false){
            var id=args.id.toString()
            if(!id.equals("noid")){
                etSearchBox.visibility=View.GONE
                btnSearch.visibility=View.GONE
                viewModel.getWordWithID(id!!)
                /*
                btnSave.setImageResource(R.drawable.img_1)
                btnNote.visibility=View.VISIBLE
                tvGhost.setText("saved")*/
            }
            else{
                etSearchBox.visibility=View.VISIBLE
                btnSearch.visibility=View.VISIBLE
            }

        }

        btnSearch.setOnClickListener {
            var wordSearch=etSearchBox.text.toString().trim()
            wordSearch?.let {
                println("word ${wordSearch} is being searched")
                viewModel.getWord(wordSearch,requireContext())
                observeLiveData()
                adapter.notifyDataSetChanged()
            }
        }

        val btnSave=view.findViewById<ImageButton>(R.id.btnSave)
        btnSave.setOnClickListener {
            viewModel.saveWord()
            observeLiveData()
        }

        btnNote.setOnClickListener{
            if(viewModel.meaningsSaved.value == true){
                var action = DictionaryFragmentDirections.actionDictionaryFragmentToNoteFragment(viewModel.meanings.value!!.id)
                Navigation.findNavController(it).navigate(action)
            }
        }

        observeLiveData()

    }





    fun observeLiveData(){

        viewModel.meanings.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                println("it is not null")
                recyclerDictionary.visibility=View.VISIBLE
                var word=it
                etWord.setText(word.word)
                etPronunce.setText(word.phonetic.get(0).text)
                adapter.refreshMeanings(word.meanings)

            }
        })
        viewModel.meaningsErrors.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    Toast.makeText(context,"there is and error occured", Toast.LENGTH_LONG).show()
                    //buraya daha sonra hata mesajı ekelenbilir
                }
            }
        })
        viewModel.meaningsLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(context,"Word is loading", Toast.LENGTH_LONG).show()// daha sonra progress bar eklenebilir
            }
        })
        viewModel.meaningsSaved.observe(viewLifecycleOwner, Observer {
            if(it){
                btnSave.setImageResource(R.drawable.img_1)
                btnNote.visibility=View.VISIBLE
            }else{
                btnSave.setImageResource(R.drawable.img)
                btnNote.visibility=View.GONE
            }
        })
    }
}