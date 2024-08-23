package com.emrsa.quizapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrsa.quizapp.R
import com.emrsa.quizapp.adapter.AnswerCheckAdapter
import com.emrsa.quizapp.databinding.FragmentAnswerCheckBinding
import com.emrsa.quizapp.model.OnItemClickListener

val trueFalseEmptyCounter = arrayListOf(0,0,0)//index 0: true, index 1: false, index 2: empty
class AnswerCheck: Fragment(), OnItemClickListener {
    private var _binding: FragmentAnswerCheckBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnswerCheckBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.recyclerViewCheck.layoutManager = LinearLayoutManager(requireContext())

        val adapter = AnswerCheckAdapter(answerList,this@AnswerCheck)
        binding.recyclerViewCheck.adapter = adapter

        binding.goToResultButton.setOnClickListener {
            findNavController().navigate(R.id.action_answerCheck_to_results)
        }

        binding.AnswerCounter.text = "True : ${trueFalseEmptyCounter[0]}\n" +
                "False : ${trueFalseEmptyCounter[1]}\n" +
                "Empty : ${trueFalseEmptyCounter[2]}"//showing true, false and empty answer counter

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle().apply {
            putInt("question_number", position)//sending question number to showQuestion
            putBoolean("is_end", true)
            //!! since this app uses one fragment (ShowQuestion) to both solving and checking questions
            //is_end checks if user is solving questions or checking answers
            //true means solving is ended, user is checking answers
        }
        findNavController().navigate(R.id.action_answerCheck_to_showQuestion, bundle)
    }
}