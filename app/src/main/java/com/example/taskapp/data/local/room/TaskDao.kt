package com.example.taskapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskapp.ui.home.TaskMode


@Dao
interface TaskDao {

    @Insert
    fun insert(task: TaskMode?)

    @Query("SELECT * FROM TaskMode")
    fun getAllTask(): List<TaskMode>?


}