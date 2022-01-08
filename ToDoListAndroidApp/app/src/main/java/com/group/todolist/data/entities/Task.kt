package com.group.todolist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "task_name")
    val taskName:String,
    @ColumnInfo(name = "due_date")
    val dueDate: String,
    @ColumnInfo(name = "category_id")
    val categoryId:Int,
    @ColumnInfo(name = "is_completed", defaultValue = "0") // added in ver2
    var isCompleted:Int
)
