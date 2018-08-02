package com.maximeroussy.invitrode.data.words

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val word: String,
    val date: Date = Date()
)
