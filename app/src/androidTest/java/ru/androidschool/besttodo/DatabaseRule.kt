package ru.androidschool.besttodo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import ru.androidschool.besttodo.data.database.TaskDatabase

@RunWith(AndroidJUnit4::class)
class DatabaseRule : TestWatcher() {

    lateinit var appDatabase: TaskDatabase

    override fun starting(description: Description?) {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    override fun finished(description: Description?) {
        appDatabase.close()
    }
}