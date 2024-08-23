package com.emrsa.quizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrsa.quizapp.model.OnItemClickListener
import com.emrsa.quizapp.model.Question
import com.emrsa.quizapp.databinding.RecyclerRowBinding
import com.emrsa.quizapp.view.answerList

class QuestionAdapter(
    private val questionList : ArrayList<Question>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.row.text = "Question ${questionList[position].question_number}"
        if(answerList[position] != 0){// if question is answered checking box in the right side of row
            holder.binding.checkBox.isChecked = true
        }
        holder.itemView.setOnClickListener{
            listener.onItemClick(position + 1)

        }
    }

}