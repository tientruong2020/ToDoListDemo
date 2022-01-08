package com.group.todolist.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.group.todolist.data.entities.Category
import com.group.todolist.data.entities.CategoryWithTasks
import com.group.todolist.data.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category_table ORDER BY id ASC")
    fun getAllCategories(): Flow<List<Category>>

    @Delete
    suspend fun deleteCategory(category: Category)

    @Delete
    suspend fun deleteTask(task: Task)

    @Transaction
    @Query("SELECT * FROM category_table WHERE id = :categoryId")
    fun getCategoryWithTasks(categoryId: Int): Flow<CategoryWithTasks>

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks():Flow<List<Task>>
}