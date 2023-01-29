package com.example.taskapp.data.local.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapp.ui.home.TaskMode

@Database(entities = [TaskMode::class], version = 1)
abstract class  DataBase : RoomDatabase() {
    abstract fun TaskDao(): TaskDao?
}