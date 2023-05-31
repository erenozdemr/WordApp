
package com.example.wordapp

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.MediaController
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
import com.example.wordapp.databinding.FragmentDictionaryBinding
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import java.util.Locale


class DictionaryFragment : Fragment(),TextToSpeech.OnInitListener {
    lateinit var binding: FragmentDictionaryBinding
    private lateinit var viewModel: WordMeaningsWievModel
    private var adapter= WordMeaningsRecyclerAdapter(arrayListOf())
    lateinit var recyclerDictionary: RecyclerView
    private lateinit var tts:TextToSpeech
    val args: DictionaryFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentDictionaryBinding.inflate(inflater,container,false)
        tts=TextToSpeech(requireContext(),this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        recyclerDictionary=view.findViewById(R.id.dictionaryRecycler)
        recyclerDictionary.layoutManager= LinearLayoutManager(view.context)
        recyclerDictionary.adapter=adapter


        viewModel= ViewModelProvider(this).get(WordMeaningsWievModel::class.java)
        viewModel.inTheBeginning()






        if(arguments?.isEmpty == false){
            var id=args.id.toString()
            if(!id.equals("noid")){
                binding.etSearchBox.visibility=View.GONE
                binding.btnSearch.visibility=View.GONE
                viewModel.getWordWithID(id!!)
                /*
                btnSave.setImageResource(R.drawable.img_1)
                btnNote.visibility=View.VISIBLE
                tvGhost.setText("saved")*/
            }
            else{
                binding.etSearchBox.visibility=View.VISIBLE
                binding.btnSearch.visibility=View.VISIBLE
            }

        }

        binding.btnSearch.setOnClickListener {
            var wordSearch=binding.etSearchBox.text.toString().trim()
            wordSearch?.let {
                println("word ${wordSearch} is being searched")
                viewModel.getWord(wordSearch,requireContext())
                observeLiveData()
                adapter.notifyDataSetChanged()
            }
        }


        binding.btnSave.setOnClickListener {
            viewModel.saveWord()
            observeLiveData()
        }

        binding.btnNote.setOnClickListener{
            if(viewModel.meaningsSaved.value == true){
                var action = DictionaryFragmentDirections.actionDictionaryFragmentToNoteFragment(viewModel.meanings.value!!.id)
                Navigation.findNavController(it).navigate(action)
            }
        }
        binding.btnSpeak.setOnClickListener{

            tts.speak(viewModel.meanings.value!!.word,TextToSpeech.QUEUE_FLUSH,null,"")
        }


        observeLiveData()

    }





    fun observeLiveData(){

        viewModel.meanings.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                binding.LinearLayoutDictionary.visibility=View.VISIBLE
                binding.LinearLayoutTextview.visibility=View.GONE
                recyclerDictionary.visibility=View.VISIBLE
                var word=it
                binding.etWord.setText(word.word)
                binding.etPronunce.setText(word.phonetic.get(0).text)
                adapter.refreshMeanings(word.meanings)

            }
            else{
                binding.LinearLayoutDictionary.visibility=View.GONE
            }
        })
        viewModel.meaningsErrors.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    binding.LinearLayoutTextview.visibility=View.VISIBLE

                    //buraya daha sonra hata mesajı ekelenbilir
                }else{
                    binding.LinearLayoutTextview.visibility=View.GONE

                }
            }
        })
        viewModel.meaningsLoading.observe(viewLifecycleOwner, Observer {
            if(it){

                binding.LinearLayoutProgressbar.visibility=View.VISIBLE
            }else{
                binding.LinearLayoutProgressbar.visibility=View.GONE
            }
        })
        viewModel.meaningsSaved.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.btnSave.setImageResource(R.drawable.img_1)
                binding.btnNote.visibility=View.VISIBLE
            }else{
                binding.btnSave.setImageResource(R.drawable.img)
                binding.btnNote.visibility=View.GONE
            }
        })
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts.setLanguage(Locale.UK)
            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Laguage is not supported")
            }
        }else{
            Log.e("TTS","Initalization failed")
        }
    }
}