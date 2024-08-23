package com.emrsa.quizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrsa.quizapp.R
import com.emrsa.quizapp.databinding.AnswerCheckRowBinding
import com.emrsa.quizapp.model.OnItemClickListener
import com.emrsa.quizapp.view.Questions


class AnswerCheckAdapter(private val answerList : ArrayList<Int>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<AnswerCheckAdapter.AnswerViewHolder>() {
        class AnswerViewHolder(val binding : AnswerCheckRowBinding) : RecyclerView.ViewHolder(binding.root){
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = AnswerCheckRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnswerViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return answerList.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
            holder.binding.row.text = "Question ${position+1}"
        if(Questions().trueAnswerList[position] != answerList[position]){
            holder.binding.imageView.setImageResource(R.drawable.check_image2)//image to show in rows after finishing quiz if answer is wrong
            //this is adjusted as checkImage2 (true) as default
        }
        holder.binding.row.setOnClickListener{
            listener.onItemClick(position + 1)
        }
    }
}