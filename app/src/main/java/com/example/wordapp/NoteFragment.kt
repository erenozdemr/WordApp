package com.example.wordapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import com.example.wordapp.MVVM.WordNoteViewModel
import com.example.wordapp.databinding.FragmentNoteBinding
import java.util.zip.Inflater


class NoteFragment : DialogFragment() {
lateinit var binding: FragmentNoteBinding
private val args:NoteFragmentArgs by navArgs()
    lateinit var  viewModel:WordNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(WordNoteViewModel::class.java)

        var id=args.id.toString()

        if(!id.isNullOrBlank()&&!id.equals("noid")){
            viewModel.getWordnoteWithID(id)
            observeLiveData()

        }else{
            onDestroy()
        }
        binding.btnNoteSave.setOnClickListener {
            var text=binding.etNote.text.toString()
            println(text)
            println(id)

            if(!id.isNullOrBlank()&&!id.equals("noid")&&text!=null){
                viewModel.updateWordnoteWithID(id,text)
                onDestroy()

            }
        }
        observeLiveData()



    }
    fun observeLiveData(){
        viewModel.note.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                binding.etNote.setText(it)
            }
        })
    }


}