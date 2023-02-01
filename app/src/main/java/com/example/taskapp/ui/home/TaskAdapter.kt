package com.example.taskapp.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R
import com.example.taskapp.databinding.ItemTaskBinding

class TaskAdapter(
    private var onLongClick: (Int) -> Unit,
    private var onUpdateClick: (TaskMode) -> Unit,
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var arrayTask = arrayListOf<TaskMode>()

//    fun addTask(taskMode: List<TaskMode>) {
//        arrayTask.add(taskMode)
//        Log.e("ololo", "addTask" + taskMode.title)
//        notifyDataSetChanged()
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTasks(list: List<TaskMode>) {
        arrayTask.clear()
        arrayTask.addAll(list)
        notifyDataSetChanged()
    }

    fun getTask(position: Int): TaskMode {
        return arrayTask[position]
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
        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.black)
        } else {
            holder.itemView.setBackgroundResource(R.color.white)
        }
    }

    override fun getItemCount(): Int {
        return arrayTask.size
    }

    inner class ViewHolder(private var binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun OnBind(taskMode: TaskMode) {
            binding.tvTitle.text = taskMode.title
            binding.tvDesc.text = taskMode.desc

            if (adapterPosition % 2 == 0) {
                binding.tvTitle.setTextColor(Color.WHITE)
                binding.tvDesc.setTextColor(Color.WHITE)
                binding.lineRv.setBackgroundColor(Color.WHITE)
            }

            itemView.setOnLongClickListener {
                Log.e("ololo", "OnBind: $adapterPosition")
                onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }
}
