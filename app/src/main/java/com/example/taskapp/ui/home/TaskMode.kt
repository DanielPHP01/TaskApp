package com.example.taskapp.ui.home


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TaskMode(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var desc: String? = null
)
