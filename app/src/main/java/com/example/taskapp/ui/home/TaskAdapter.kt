package com.example.taskapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.databinding.ItemTaskBinding

class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var arrayTask = arrayListOf<TaskMode>()

    fun addTask(taskMode: TaskMode) {
        arrayTask.add(taskMode)
        Log.e("ololo", "addTask" + taskMode.title)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.OnBind(
            arrayTask[position]
        )
    }

    override fun getItemCount(): Int {
        return arrayTask.size
    }

    inner class ViewHolder(private var binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun OnBind(taskMode: TaskMode) {
            binding.tvTitle.text = taskMode.title
            binding.tvDesc.text = taskMode.desc
        }
    }
}