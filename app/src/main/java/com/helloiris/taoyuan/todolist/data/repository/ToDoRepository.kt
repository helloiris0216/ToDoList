package com.helloiris.taoyuan.todolist.data.repository

import androidx.lifecycle.LiveData
import com.helloiris.taoyuan.todolist.data.ToDoDao
import com.helloiris.taoyuan.todolist.data.models.ToDoData

class ToDoRepository(private val dao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = dao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        dao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        dao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData) {
        dao.deleteData(toDoData)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}