package com.helloiris.taoyuan.todolist.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.helloiris.taoyuan.todolist.data.ToDoDao
import com.helloiris.taoyuan.todolist.data.ToDoDatabase
import com.helloiris.taoyuan.todolist.data.models.ToDoData
import com.helloiris.taoyuan.todolist.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel (application: Application): AndroidViewModel(application){
    private val toDoDao: ToDoDao
    private val repository: ToDoRepository
    val getAllData: LiveData<List<ToDoData>>

    init {
        toDoDao = ToDoDatabase.getDatabase(application).todoDao()
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
    }

    // Coroutine: insert data in the background thread.
    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}