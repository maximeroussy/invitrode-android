package com.maximeroussy.invitrode.data.words

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface WordDao {
  @Query("SELECT * FROM word")
  fun getAll(): LiveData<List<Word>>

  @Query("SELECT * FROM word WHERE id LIKE :value")
  fun getById(value: Long): Word

  @Query("SELECT * FROM word WHERE word LIKE :value")
  fun getByWord(value: String): Word

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(newWord: Word)

  @Delete
  fun delete(wordToDelete: Word)
}
