package ru.androidschool.besttodo.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,
    @ColumnInfo(name = "category")
    val category: TaskCategory
)

@Entity(tableName = "subtask")
data class Subtask(
    @PrimaryKey(autoGenerate = true)
    val subtaskId: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,
    @ColumnInfo(name = "parentTaskId")
    val parentTaskId: Int
)

data class Task(
    @Embedded
    val parentTask: TaskEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentTaskId"
    )
    val subtasks: List<Subtask>
)