package com.maximeroussy.invitrode.presentation.favoritelist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.maximeroussy.invitrode.data.InvitrodeDatabase
import com.maximeroussy.invitrode.data.words.Word
import com.maximeroussy.invitrode.data.words.WordDao
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {
  private val wordDao: WordDao = InvitrodeDatabase.getInstance(application).wordDao()

  fun getWordList(): LiveData<List<Word>> {
    return wordDao.getAll()
  }

  fun deleteWords(wordsToDelete: List<Word>) {
    launch(UI) {
      databaseDelete(wordsToDelete).await()
    }
  }

  private fun databaseDelete(wordsToDelete: List<Word>): Deferred<Boolean> {
    return async(CommonPool) {
      wordsToDelete.forEach { wordDao.delete(it) }
      true
    }
  }
}
