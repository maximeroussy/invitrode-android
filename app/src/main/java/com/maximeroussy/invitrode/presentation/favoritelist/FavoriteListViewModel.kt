package com.maximeroussy.invitrode.presentation.favoritelist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.maximeroussy.invitrode.data.InvitrodeDatabase
import com.maximeroussy.invitrode.data.words.Word
import com.maximeroussy.invitrode.data.words.WordDao

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {
  private val wordDao: WordDao = InvitrodeDatabase.getInstance(application).wordDao()

  fun getWordList(): LiveData<List<Word>> {
    return wordDao.getAll()
  }
}
