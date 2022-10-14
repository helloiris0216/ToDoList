package com.helloiris.taoyuan.todolist.fragments

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.helloiris.taoyuan.todolist.R

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
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }
    }
}