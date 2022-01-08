package com.group.todolist.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.group.todolist.data.entities.Category
import com.group.todolist.data.entities.CategoryWithTasks
import com.group.todolist.data.entities.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val dao: DAO) {

    suspend fun insertCategory(category: Category){
        dao.insertCategory(category)
    }

    fun getAllCategory():Flow<List<Category>>{
        return dao.getAllCategories()
    }

    suspend fun deleteCategory(category: Category){
        dao.deleteCategory(category)
    }

    suspend fun addTask(task: Task){
        dao.insertTask(task)
    }

    fun getTasksByCategory(categoryId: Int):Flow<CategoryWithTasks>{
        return dao.getCategoryWithTasks(categoryId)
    }

    suspend fun deleteTask(task: Task){
        dao.deleteTask(task)
    }

    suspend fun updateTask(task: Task){
        dao.updateTask(task)
    }

    fun getAllTasks():Flow<List<Task>>{
        return dao.getAllTasks()
    }
}