package com.maximeroussy.invitrode.presentation.generateword

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.maximeroussy.invitrode.RandomWord
import com.maximeroussy.invitrode.data.InvitrodeDatabase
import com.maximeroussy.invitrode.data.words.Word
import com.maximeroussy.invitrode.data.words.WordDao
import com.maximeroussy.invitrode.util.SingleLiveEvent
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.Random
import java.util.Stack

class GenerateWordViewModel(application: Application) : AndroidViewModel(application) {
  val word = ObservableField<String>("INVITRODE")
  val wordLength = ObservableField<String>("3")
  val generateButtonEnabled = ObservableBoolean(true)
  val favoriteButtonEnabled = ObservableBoolean(true)
  val specifyWordLengthEnabled = ObservableBoolean(false)
  val isWordFavorited = ObservableBoolean(false)
  private val showSavedToFavorites = SingleLiveEvent<Any>()
  private val showRemovedFromFavorites = SingleLiveEvent<Any>()
  private val showSaveToFavoritesError = SingleLiveEvent<Any>()
  private val showRemoveFromFavoritesError = SingleLiveEvent<Any>()
  private val random = Random()
  private val wordStack = Stack<String>()
  private val wordDao: WordDao = InvitrodeDatabase.getInstance(application).wordDao()
  private var isTwoWords = false

  val getShowSavedToFavorites : LiveData<Any>
    get() = showSavedToFavorites

  val getShowRemovedFromFavorites : LiveData<Any>
    get() = showRemovedFromFavorites

  val getShowSaveToFavoritesError : LiveData<Any>
    get() = showSaveToFavoritesError

  val getShowRemoveFromFavoritesError : LiveData<Any>
    get() = showRemoveFromFavoritesError

  fun onFavoriteButtonClicked() {
    favoriteButtonEnabled.set(false)
    isWordFavorited.set(!isWordFavorited.get())
    if (isWordFavorited.get()) {
      launch(UI) {
        try {
          saveWordToFavorites(Word(word = word.get().toString()))
          showSavedToFavorites.call()
          favoriteButtonEnabled.set(true)
        } catch (e: Exception) {
          showSaveToFavoritesError.call()
        }
      }
    } else {
      launch(UI) {
        try {
          removeWordFromFavorites(word.get().toString())
          showRemovedFromFavorites.call()
          favoriteButtonEnabled.set(true)
        } catch (e: Exception) {
          showRemoveFromFavoritesError.call()
        }
      }
    }
  }

  fun onGenerateNewWordButtonClicked() {
    wordStack.push(word.get())
    val newWord = generateRandomWord()
    resetFavorite()
    word.set(newWord)
  }

  fun onPreviousWordButtonClicked() {
    if (!wordStack.empty()) {
      word.set(wordStack.pop())
      resetFavorite()
    }
  }

  fun onSpecifyWordLengthOptionChanged(value: Boolean) {
    specifyWordLengthEnabled.set(value)
  }

  fun onTwoWordsOptionChanged(value: Boolean) {
    isTwoWords = value
  }

  fun onWordLengthChanged(value: Int, fromUser: Boolean) {
    if (fromUser) {
      // minimum word length is enforced to 3
      wordLength.set((value + 3).toString())
    }
  }

  private fun generateRandomWord(): String {
    return when (specifyWordLengthEnabled.get()) {
      true -> {
        val length = wordLength.get()!!.toInt()
        when (isTwoWords) {
          true -> RandomWord.getNewWord(length) + " " + RandomWord.getNewWord(length)
          false -> RandomWord.getNewWord(length)
        }
      }
      false -> {
        when (isTwoWords) {
          true -> RandomWord.getNewWord(rand(3, 12)) + " " + RandomWord.getNewWord(rand(3, 12))
          false -> RandomWord.getNewWord(rand(3, 12))
        }
      }
    }
  }

  private fun resetFavorite() {
    if (isWordFavorited.get()) {
      isWordFavorited.set(false)
    }
  }

  private suspend fun saveWordToFavorites(word: Word) = withContext(CommonPool) {
    wordDao.insert(word)
  }

  private suspend fun removeWordFromFavorites(value: String) = withContext(CommonPool) {
    val word = wordDao.getByWord(value)
    wordDao.delete(word)
  }

  private fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
  }
}
