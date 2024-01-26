package ru.androidschool.besttodo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.androidschool.besttodo.data.model.Subtask
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.data.model.TaskCategory
import java.util.concurrent.Executors

const val DATABASE_VERSION_CODE = 2

@Database(
    entities = [TaskEntity::class, Subtask::class],
    version = DATABASE_VERSION_CODE,
    exportSchema = true
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(
            context: Context
        ): TaskDatabase? {
            if (INSTANCE == null) {
                synchronized(TaskDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TaskDatabase::class.java, "best_todo_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        val PREPOPULATE_DATA = listOf(
            TaskEntity(
                1,
                "Прочитать книгу Рефакторинг",
                false,
                category = TaskCategory.STUDY
            ),
            TaskEntity(
                2,
                "Купить авиабилеты",
                false,
                category = TaskCategory.PERSONAL
            )
        )
    }
}