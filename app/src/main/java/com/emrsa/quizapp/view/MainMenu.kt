package com.emrsa.quizapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emrsa.quizapp.R
import com.emrsa.quizapp.databinding.ActivityMainMenuBinding

class MainMenu : Fragment() {
    private var _binding: ActivityMainMenuBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityMainMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_questions2)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}