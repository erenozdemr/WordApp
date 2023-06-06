package com.example.wordapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.wordapp.MVVM.Question
import com.example.wordapp.MVVM.QuizViewModel
import com.example.wordapp.MVVM.SwipeWordViewModel
import com.example.wordapp.MVVM.Word
import com.example.wordapp.databinding.FragmentQuizBinding
import com.example.wordapp.databinding.FragmentSwipeBinding
import kotlinx.android.synthetic.main.fragment_quiz.*


class QuizFragment : Fragment(),View.OnClickListener {
    lateinit var viewModel: QuizViewModel
    lateinit var binding: FragmentQuizBinding
    private var questList:ArrayList<Question>?=null
    private var quesNumber=0
    private var choosenView:TextView?=null
    private var correctNumber=0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentQuizBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(this).get(QuizViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTenQuestion()
        observeLiveData(view)
        binding.opOne.setOnClickListener(this)
        binding.opTwo.setOnClickListener(this)
        binding.opThree.setOnClickListener(this)
        binding.opFour.setOnClickListener(this)
        binding.submitButton.setOnClickListener(this)







    }

    private fun showNextQuestion(v:View) {
        if (quesNumber<10){
            defaultView()
            choosenView=null
            binding.tvQuestion.setText("Which word has a meaning like this in the below?: \n ${questList!!.get(quesNumber).word}")
            binding.opOne.setText(questList!!.get(quesNumber).op1)
            binding.opTwo.setText(questList!!.get(quesNumber).op2)
            binding.opThree.setText(questList!!.get(quesNumber).op3)
            binding.opFour.setText(questList!!.get(quesNumber).op4)
            binding.progressBar.progress=quesNumber+1
            binding.tvProgress.text = "${quesNumber+1}/10"
        }
        else{
            val mAlertDialog = AlertDialog.Builder(requireContext()) // This needs the activity's context

            mAlertDialog.setCancelable(false)
            mAlertDialog.setOnDismissListener {
                onDestroyView()
            }
            mAlertDialog.setMessage("Congrats !! \nYou have $correctNumber correct answer!! \nCongrats !! ")
            mAlertDialog.setPositiveButton("Finish", DialogInterface.OnClickListener { dialog,
                                                                                      which ->
                var action=QuizFragmentDirections.actionQuizFragmentToMainFragment()
                v.findNavController().navigate(action)
            })

            mAlertDialog.show()

        }


    }
    private fun defaultView(){
        binding.opOne.background=ContextCompat.getDrawable(requireContext(),R.drawable.unselected)
        binding.opTwo.background=ContextCompat.getDrawable(requireContext(),R.drawable.unselected)
        binding.opThree.background=ContextCompat.getDrawable(requireContext(),R.drawable.unselected)
        binding.opFour.background=ContextCompat.getDrawable(requireContext(),R.drawable.unselected)

    }
    private fun makeWrong() {
        if(choosenView!=null){
            if(choosenView!!.text!=questList!!.get(quesNumber).answer){
                choosenView!!.background=ContextCompat.getDrawable(requireContext(),R.drawable.wrong)
            }
        }




    }
    private fun answerView() {
        makeWrong()
        when (questList!!.get(quesNumber).answer) {
            binding.opOne.text-> binding.opOne.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct)
            binding.opTwo.text-> binding.opTwo.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct)
            binding.opThree.text-> binding.opThree.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct)
            binding.opFour.text-> binding.opFour.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct)

        }
    }
    private fun selectedOpView(t: TextView) {
        choosenView=t
        defaultView()
        t.background = ContextCompat.getDrawable(requireContext(), R.drawable.edge)

    }

    fun observeLiveData(v:View){
        viewModel.questions.observe(viewLifecycleOwner, Observer {
            if(it!=null){

                questList=it
                binding.submitButton.setText("SUBMIT")
                showNextQuestion(v)
            }

        })
        viewModel.quesError.observe(viewLifecycleOwner,Observer{
            if(it){
                binding.quizFragmentLinearLayout.visibility=View.GONE
                binding.QuizFragmentErrorLayout.visibility=View.VISIBLE
            }else{
                binding.quizFragmentLinearLayout.visibility=View.VISIBLE
                binding.QuizFragmentErrorLayout.visibility=View.GONE
            }
        })
    }
    private fun isCorrect():Boolean{
        var flag=false
        if(choosenView!=null){
            flag= choosenView!!.text==questList!!.get(quesNumber).answer
        }
        answerView()
        return flag
    }

    override fun onClick(v: View?) {
        if (binding.submitButton.text=="SUBMIT") {
            when (v?.id) {

                R.id.opOne -> binding.opOne.let { selectedOpView(binding.opOne) }
                R.id.opTwo -> binding.opTwo.let { selectedOpView(binding.opTwo) }
                R.id.opThree -> binding.opThree.let { selectedOpView(binding.opThree) }
                R.id.opFour -> binding.opFour.let { selectedOpView(binding.opFour) }
                R.id.submitButton -> {
                    binding.submitButton.setText("NEXT")
                    if(isCorrect()){
                        correctNumber++
                    }
                }

            }
        } else {
            when (v?.id) {
                R.id.submitButton -> {
                    binding.submitButton.setText("SUBMIT")
                    quesNumber++
                    showNextQuestion(v)
                }
            }

        }
    }


}