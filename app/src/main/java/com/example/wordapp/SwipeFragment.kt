package com.example.wordapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.AnimBuilder
import androidx.navigation.Navigation
import com.example.wordapp.MVVM.SwipeWordViewModel
import com.example.wordapp.MVVM.Word
import com.example.wordapp.databinding.FragmentSwipeBinding
import kotlin.random.Random


class SwipeFragment : Fragment() {
   lateinit var viewModel: SwipeWordViewModel
   lateinit var binding: FragmentSwipeBinding
   private var wordList:ArrayList<Word>?=null
    private var index=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSwipeBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(this).get(SwipeWordViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWords()
        observeLiveData()

        binding.btnSwipeGo.setOnClickListener {
            wordList.let {
                val action=SwipeFragmentDirections.actionSwipeFragmentToDictionaryFragment(wordList!!.get(index).id)
                Navigation.findNavController(view).navigate(action)
            }
        }
        binding.btnSwipeNext.setOnClickListener {
            wordList.let {
                if(it!=null){
                    index= Random.nextInt(0,it.size)
                    showWord(it.get(index))

                }
            }
        }
        binding.cwSwipe.setOnClickListener {
            if(wordList!=null){
                var situation=binding.tvGhost.text.toString()
                var anim=AnimationUtils.loadAnimation(this.requireContext(),R.anim.rotate_card_anim)
                anim.duration=300


                binding.cwSwipe.animation=anim
                if(situation.equals("word")){
                    showMeaning(wordList!!.get(index))
                }else{
                    showWord(wordList!!.get(index))
                }



            }
        }



    }
    fun observeLiveData(){
        viewModel.words.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                wordList=it
                index=Random.nextInt(0,it.size)
                showWord(it.get(index))


            }
        })
    }
    fun showWord(word:Word){
        binding.tvSwipe.setText(word.word)
        binding.tvGhost.setText("word")

    }
    fun showMeaning(word: Word){
        binding.tvSwipe.setText(word.meanings.get(0).definitions.get(0).definition)
        binding.tvGhost.setText("meaning")

    }


}