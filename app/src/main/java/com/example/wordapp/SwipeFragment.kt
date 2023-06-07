package com.example.wordapp

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
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
        val rotateAnimator = ObjectAnimator.ofFloat(binding.cwSwipe, "rotationY", 0f, 180f)
        rotateAnimator.duration = 300
        rotateAnimator.interpolator = AccelerateDecelerateInterpolator()

        rotateAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                binding.tvSwipe.setText("")
            }

            override fun onAnimationEnd(animation: Animator) {
                if (binding.cwSwipe.rotationY == 180f) {
                    binding.tvSwipe.scaleX = -1f
                } else {
                    binding.tvSwipe.scaleX = 1f
                }
                setTV()
            }

            override fun onAnimationCancel(animation: Animator) {setTV()}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        binding.cwSwipe.setOnClickListener {
            if(wordList!=null){
                
                rotateAnimator.start()

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
    fun setTV(){
        var situation=binding.tvGhost.text.toString()

        if(situation.equals("word")){
            showMeaning(wordList!!.get(index))
        }else{
            showWord(wordList!!.get(index))
        }
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