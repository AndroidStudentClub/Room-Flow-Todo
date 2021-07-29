package ru.androidschool.besttodo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import ru.androidschool.besttodo.data.database.TaskDatabase
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class DatabaseTest {

    protected    lateinit var appDatabase: TaskDatabase
    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }
}