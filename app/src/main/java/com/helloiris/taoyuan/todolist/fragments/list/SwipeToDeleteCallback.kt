package com.helloiris.taoyuan.todolist.fragments.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/*
 * Package com.helloiris.taoyuan.todolist.fragments.list
 *
 * Module: SwipeToDeleteCallback
 * -----------------------------------------------
 * Created by Iris YEN on 2022/10/20
 * Last saved on Oct-20-2022 11:41
 * */
abstract class SwipeToDeleteCallback: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
}