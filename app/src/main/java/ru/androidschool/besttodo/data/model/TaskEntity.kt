package ru.androidschool.besttodo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "isCompleted") val isCompleted: Boolean,

    @ColumnInfo(name = "category")
    val category: TaskCategory
)