package com.helloiris.taoyuan.todolist.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.helloiris.taoyuan.todolist.R
import com.helloiris.taoyuan.todolist.data.models.Priority
import com.helloiris.taoyuan.todolist.data.models.ToDoData
import com.helloiris.taoyuan.todolist.fragments.list.ListFragmentDirections

/*
 * Package com.helloiris.taoyuan.todolist.fragments
 * Module: BindingAdapter
 * -----------------------------------------------
 * Created by Iris YEN on 2022/10/14
 * Last saved on Oct-14-2022 11:34
 * */
class BindingAdapters {
    companion object {
        // used to the floating button: click the button -> navigate to the add fragment
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        // observe the empty database
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when(emptyDatabase.value) {
                false -> view.visibility = View.INVISIBLE
                else -> view.visibility = View.VISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            val value = when(priority) {
                Priority.HIGH -> 0
                Priority.MEDIUM -> 1
                Priority.LOW -> 2
            }
            view.setSelection(value)
        }

        @BindingAdapter("android:parsePriorityToColor")
        @JvmStatic
        fun parsePriorityToColor(view: CardView, priority: Priority) {
            val color = when(priority) {
                Priority.HIGH -> view.context.getColor(R.color.red)
                Priority.MEDIUM -> view.context.getColor(R.color.yellow)
                Priority.LOW -> view.context.getColor(R.color.green)
            }
            view.setCardBackgroundColor(color)
        }

        // navigate from the list fragment to the update fragment
        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }
}