package com.emrsa.quizapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.emrsa.quizapp.R
import com.emrsa.quizapp.databinding.FragmentResultsBinding

class Results : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val view = binding.root

        val True = trueFalseEmptyCounter[0]
        val False = trueFalseEmptyCounter[1]
        val Empty = trueFalseEmptyCounter[2]
        var resultText = binding.resultText
        val width = 300
        val height = 300

        binding.image.layoutParams.width = width
        binding.image.layoutParams.height = height

        if((False + Empty) == 0){
            //change the .checkImage1 as the image you wish
            //don't forget to put image file to drawable
            resultText.text = "RESULT TEXT1"//text to show
            binding.image.setImageResource(R.drawable.check_image1)//image name to show
        }
        else if(False + Empty == 1){
            resultText.text = "RESULT TEXT2"
            binding.image.setImageResource(R.drawable.check_image1)//image name to show
        }
        else if(False + Empty <= True){
            resultText.text = "RESULT TEXT3"
            binding.image.setImageResource(R.drawable.check_image1)//image name to show
        }
        else if(True == 0){
            resultText.text = "RESULT TEXT4"
            binding.image.setImageResource(R.drawable.check_image1)//image name to show
        }
        else{
            resultText.text = "RESULT TEXT5"
            binding.image.setImageResource(R.drawable.check_image1)//image name to show
        }

        binding.mainMenuButton.setOnClickListener {
            for(i in 0..(answerList.size-1)){
                answerList[i] = 0//resetting answerList
            }
            trueFalseEmptyCounter[0] = 0
            trueFalseEmptyCounter[1] = 0 //resetting counter array
            trueFalseEmptyCounter[2] = 0
            findNavController().navigate(R.id.action_results_to_mainMenu)//navigating to main menu
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

