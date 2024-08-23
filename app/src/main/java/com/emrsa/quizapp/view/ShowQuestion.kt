package com.emrsa.quizapp.view

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.emrsa.quizapp.model.OnItemClickListener
import com.emrsa.quizapp.R
import com.emrsa.quizapp.databinding.FragmentShowQuestionBinding

class ShowQuestion : Fragment() , OnItemClickListener {

    private var _binding: FragmentShowQuestionBinding? = null
    private val binding get() = _binding!!
    private val maxQuestionNumber = Questions().trueAnswerList.size//variable to check if user is on last question
    private val questionList = Questions().questionList//declared questionList at view/Questions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowQuestionBinding.inflate(inflater, container, false)
        val view = binding.root

        var questionNumber : Int? = arguments?.getInt("question_number")
        val isEnd : Boolean? = arguments?.getBoolean("is_end")
        //checking if user navigate here from AnswerCheck fragment
        //or from Questions fragment

        val nextButton = binding.nextQuestionButton//this button is only visible when solving
        val allQuestionsButton = binding.allQuestionsButton//this button is both visible for solving and checking

        //changing texts
        binding.questionNumberHolder.text = "Question $questionNumber"
        binding.radioButton1.text = questionList[questionNumber!!-1].a1
        binding.radioButton2.text = questionList[questionNumber-1].a2
        binding.radioButton3.text = questionList[questionNumber-1].a3
        binding.questionText.text = questionList[questionNumber-1].question

        if(!isEnd!!){//USER IS CURRENTLY AT FRAGMENT QUESTIONS, USER IS STILL SOLVING QUESTIONS
            if(questionNumber == maxQuestionNumber) {//user chose last question from list
                binding.nextQuestionButton.text = "End Quiz"
                binding.nextQuestionButton.setOnClickListener {

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Are You Sure?")
                    builder.setMessage("Are you sure you want to end quiz?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        answerList.forEachIndexed{index, value ->
                            if(value == 0){//answer is empty
                                trueFalseEmptyCounter[2]++
                            }
                            else if(value != Questions().trueAnswerList[index]){//answer is false
                                trueFalseEmptyCounter[1]++
                            }
                            else{//answer is true
                                trueFalseEmptyCounter[0]++
                            }
                        }
                        findNavController().navigate(R.id.action_showQuestion_to_answerCheck)
                    }
                    builder.setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
            else {//user did not choose last question from list
                nextButton.setOnClickListener {
                    nextQuestion(questionNumber, answerList[questionNumber])
                    questionNumber++
                }
            }
            when (answerList[questionNumber - 1]) {
                //this is for selecting the radiobutton user already selected
                //if user navigates to another fragment
                0 -> {
                    binding.radioGroup.clearCheck()
                }

                1 -> {
                    binding.radioGroup.check(R.id.radioButton1)
                }

                2 -> {
                    binding.radioGroup.check(R.id.radioButton2)
                }

                3 -> {
                    binding.radioGroup.check(R.id.radioButton3)
                }
            }
            binding.radioButton1.setOnClickListener {
                if(answerList[questionNumber-1] == 1){//if answer is already selected, to being able to undo our choice
                    //by clicking on the selected answer again
                    Questions().updateAnswerList(questionNumber, 0)
                    binding.radioButton1.isChecked = false
                }else {
                    Questions().updateAnswerList(questionNumber, 1)
                }
            }
            binding.radioButton2.setOnClickListener {
                if(answerList[questionNumber-1] == 2){//if answer is already selected, to being able to undo our choice
                    Questions().updateAnswerList(questionNumber, 0)
                    binding.radioButton2.isChecked = false
                }else {
                    Questions().updateAnswerList(questionNumber, 2)
                }
            }
            binding.radioButton3.setOnClickListener {
                if (answerList[questionNumber - 1] == 3) {//if answer is already selected, to being able to undo our choice
                    Questions().updateAnswerList(questionNumber, 0)
                    binding.radioButton3.isChecked = false
                }
                else{
                Questions().updateAnswerList(questionNumber, 3)
                }
            }

            allQuestionsButton.setOnClickListener {
                findNavController().navigate(R.id.action_showQuestion_to_questions)
            }
        }
        else{//USER IS CURRENTLY AT FRAGMENT ANSWER_CHECK, INSPECTING ANSWERS
            nextButton.isEnabled = false
            nextButton.isVisible = false//disabling next_question button on answer_check
            when (answerList[questionNumber - 1]) {//checking the answer user selected
                0 -> {
                    binding.radioGroup.clearCheck()
                }

                1 -> {
                    binding.radioGroup.check(R.id.radioButton1)
                }

                2 -> {
                    binding.radioGroup.check(R.id.radioButton2)
                }

                3 -> {
                    binding.radioGroup.check(R.id.radioButton3)
                }
            }
            binding.radioButton1.isEnabled = false//options are no longer selectable
            binding.radioButton2.isEnabled = false
            binding.radioButton3.isEnabled = false
            when(Questions().trueAnswerList[questionNumber-1]){//making true answer green
                1 ->{
                    binding.radioButton1.setTextColor(Color.GREEN)
                }
                2 ->{
                    binding.radioButton2.setTextColor(Color.GREEN)
                }
                3 ->{
                    binding.radioButton3.setTextColor(Color.GREEN)
                }
            }
            allQuestionsButton.setOnClickListener{
                findNavController().navigate(R.id.action_showQuestion_to_answerCheck)
            }
        }
        return view
    }
    private fun nextQuestion(questionNumber : Int, currentAns: Int){//function for next_question button
        if (questionNumber == maxQuestionNumber - 1) {//if user reaches last question by using next question button
            binding.nextQuestionButton.text = "End Quiz"
            binding.nextQuestionButton.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Are You Sure?")
                builder.setMessage("Are you sure you want to end quiz?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    answerList.forEachIndexed{index, value ->
                        if(value == 0){
                            trueFalseEmptyCounter[2]++
                        }
                        else if(value != Questions().trueAnswerList[index]){
                            trueFalseEmptyCounter[1]++
                        }
                        else{
                            trueFalseEmptyCounter[0]++
                        }
                    }
                    findNavController().navigate(R.id.action_showQuestion_to_answerCheck)
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
        //changing texts
        binding.questionNumberHolder.text = "Question ${questionNumber+1}"
        binding.radioButton1.text = questionList[questionNumber].a1
        binding.radioButton2.text = questionList[questionNumber].a2
        binding.radioButton3.text = questionList[questionNumber].a3
        binding.questionText.text = questionList[questionNumber].question
        when(currentAns){
            0 -> {
                binding.radioGroup.clearCheck()
                println("0 checked")
            }
            1 -> {
                binding.radioGroup.check(R.id.radioButton1)
                println("1 checked")
            }
            2 -> {
                binding.radioGroup.check(R.id.radioButton2)
            }
            3 -> {
                binding.radioGroup.check(R.id.radioButton3)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onItemClick(position: Int) {

    }
}