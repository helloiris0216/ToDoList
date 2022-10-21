package com.helloiris.taoyuan.todolist.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.helloiris.taoyuan.todolist.data.models.ToDoData

/*
 * Package com.helloiris.taoyuan.todolist.fragments.list.adapter
 *
 * Module: ToDoDiffUtil
 * copyright © 2022 OB APP 3.0 corporation. all rights reserved.
 * -----------------------------------------------
 *
 * Created by Iris YEN on 2022/10/20
 * Last saved on 10月-20-2022 12:21
 * */
class ToDoDiffUtil(
    private val oldList: List<ToDoData>,
    private val newList: List<ToDoData>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.description == newItem.description &&
                oldItem.priority == newItem.priority
    }
}