package com.emrsa.quizapp.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrsa.quizapp.model.OnItemClickListener
import com.emrsa.quizapp.R
import com.emrsa.quizapp.adapter.QuestionAdapter
import com.emrsa.quizapp.databinding.FragmentQuestionsBinding
import com.emrsa.quizapp.model.Question
import java.util.Collections

val answerList = ArrayList(Collections.nCopies(Questions().questionList.size, 0))
class Questions : Fragment(), OnItemClickListener {

    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    //ARRAY THAT CONTAINS QUESTIONS, ADD QUESTIONS IN FORMAT -> Question(q_num,answer,question_text,a1,a2,a3)
    //for detail, check model/Question class
    val questionList = arrayListOf(
        Question(1, 3,"QUESTION TEXT1","A1","A2","A3"),
        Question(2,1,"QUESTION TEXT2","B1","B2","B3")
    )
    var trueAnswerList = questionList.map{it.answer}//array of true answers (Question.answer)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = QuestionAdapter(questionList,this@Questions)
        binding.recyclerView.adapter = adapter

        binding.endButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Are You Sure?")
            builder.setMessage("Are you sure you want to end quiz?")
            builder.setPositiveButton("Yes") { dialog, which ->
                answerList.forEachIndexed{index, value -> // updating counter
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
                findNavController().navigate(R.id.action_questions_to_answerCheck)

            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }
        return view

    }
    fun updateAnswerList(questionNumber : Int, newAnswer : Int){
        //function to update answerList
        //there is actually no need to update func for a public array
        //but i think code is more readable with this
        answerList[questionNumber-1] = newAnswer
    }
    override fun onItemClick(position: Int) {
        val bundle = Bundle().apply {
            putInt("question_number", position)//sending question number to showQuestion
            putBoolean("is_end", false)
            //!! since this app uses one fragment (ShowQuestion) to both solving and checking questions
            //is_end checks if user is solving questions or checking answers
            //false means solving is not ended, user is still solving questions
        }
        findNavController().navigate(R.id.action_questions_to_showQuestion, bundle)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}