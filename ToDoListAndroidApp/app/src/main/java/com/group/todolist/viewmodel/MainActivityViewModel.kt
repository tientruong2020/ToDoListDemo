package com.group.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.group.todolist.data.database.RoomRepository
import com.group.todolist.data.entities.Category
import com.group.todolist.data.entities.CategoryWithTasks
import com.group.todolist.data.entities.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val roomRepository: RoomRepository):
    ViewModel() {

    val categoryLiveData = MutableLiveData<List<Category>>()

    val taskLiveData = MutableLiveData<CategoryWithTasks>()

    val tasksListLiveData = MutableLiveData<List<Task>>()

    fun addCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertCategory(category)
        }
    }

    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllCategory().collect {
                categoryLiveData.postValue(it)
            }
        }
    }

    fun deleteCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteCategory(category)
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addTask(task)
        }
    }

    fun getTasksInCategory(categoryId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getTasksByCategory(categoryId).filter {
                it.category.id == categoryId
            }.collect {
                taskLiveData.postValue(it)
            }
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteTask(task)
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.updateTask(task)
        }
    }

    fun getAllTask(){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getAllTasks().collect {
                tasksListLiveData.postValue(it)
            }
        }
    }

}