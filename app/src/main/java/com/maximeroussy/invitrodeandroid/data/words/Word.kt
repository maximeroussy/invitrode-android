package com.maximeroussy.invitrodeandroid.data.words

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val word: String,
    val date: Date = Date()
)
