package com.maximeroussy.invitrodeandroid.data.words

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
  @Query("SELECT * FROM word")
  fun getAll(): LiveData<List<Word>>

  @Query("SELECT * FROM word WHERE word LIKE :value")
  fun getByWord(value: String): Word

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(newWord: Word)

  @Delete
  fun delete(wordToDelete: Word)
}
