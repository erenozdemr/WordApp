package com.example.wordapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.wordapp.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnList.setOnClickListener {
            var action=MainFragmentDirections.actionMainFragmentToListWordsFragment()
            view.findNavController().navigate(action)
        }
        binding.btnCards.setOnClickListener {
            var action=MainFragmentDirections.actionMainFragmentToSwipeFragment()
            view.findNavController().navigate(action)
        }
        binding.btnQuiz.setOnClickListener {
            var action=MainFragmentDirections.actionMainFragmentToQuizFragment()
            view.findNavController().navigate(action)
        }
        binding.btnDictionary.setOnClickListener {
            var action=MainFragmentDirections.actionMainFragmentToDictionaryFragment("noid")
            view.findNavController().navigate(action)
        }
        binding.btnai.setOnClickListener{
         val intent=Intent(it.context,AiActivity::class.java)
        startActivity(intent)
        }


    }


}