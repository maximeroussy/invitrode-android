package com.maximeroussy.invitrodeandroid.presentation.favoritelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.maximeroussy.invitrodeandroid.data.InvitrodeDatabase
import com.maximeroussy.invitrodeandroid.data.words.Word
import com.maximeroussy.invitrodeandroid.data.words.WordDao
import com.maximeroussy.invitrodeandroid.util.SingleLiveEvent
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class FavouriteListViewModel(application: Application) : AndroidViewModel(application) {
  private val wordDao: WordDao = InvitrodeDatabase.getInstance(application).wordDao()
  private val showRemovedFromFavorites = SingleLiveEvent<Any>()
  private val showRemoveFromFavoritesError = SingleLiveEvent<Any>()

  val getShowRemovedFromFavorites : LiveData<Any>
    get() = showRemovedFromFavorites

  val getShowRemoveFromFavoritesError : LiveData<Any>
    get() = showRemoveFromFavoritesError

  fun getWordList(): LiveData<List<Word>> {
    return wordDao.getAll()
  }

  fun deleteWords(wordsToDelete: List<Word>) {
    launch(UI) {
      try {
        databaseDelete(wordsToDelete).await()
        showRemovedFromFavorites.call()
      } catch (e: Exception) {
        showRemoveFromFavoritesError.call()
      }
    }
  }

  private fun databaseDelete(wordsToDelete: List<Word>): Deferred<Boolean> {
    return async(CommonPool) {
      wordsToDelete.forEach { wordDao.delete(it) }
      true
    }
  }
}
