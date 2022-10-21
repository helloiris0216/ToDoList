package com.helloiris.taoyuan.todolist.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.helloiris.taoyuan.todolist.data.models.ToDoData
import com.helloiris.taoyuan.todolist.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    // declare an empty list to hold the to do data
    var dataList = emptyList<ToDoData>()

    class ListViewHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }
        companion object {
            fun form(parent: ViewGroup): ListViewHolder {
                val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.form(parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}