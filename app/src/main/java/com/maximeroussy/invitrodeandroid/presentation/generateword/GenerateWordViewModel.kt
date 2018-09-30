package com.maximeroussy.invitrodeandroid.presentation.generateword

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.maximeroussy.invitrode.WordGenerator
import com.maximeroussy.invitrodeandroid.data.InvitrodeDatabase
import com.maximeroussy.invitrodeandroid.data.words.Word
import com.maximeroussy.invitrodeandroid.data.words.WordDao
import com.maximeroussy.invitrodeandroid.util.SingleLiveEvent
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.*

class GenerateWordViewModel(application: Application) : AndroidViewModel(application) {
  val word = ObservableField<String>("INVITRODE")
  val wordLength = ObservableField<String>("3")
  val generateButtonEnabled = ObservableBoolean(true)
  val favouriteButtonEnabled = ObservableBoolean(true)
  val specifyWordLengthEnabled = ObservableBoolean(false)
  val isWordFavourited = ObservableBoolean(false)
  private val showSavedToFavourites = SingleLiveEvent<Any>()
  private val showRemovedFromFavourites = SingleLiveEvent<Any>()
  private val showSaveToFavouritesError = SingleLiveEvent<Any>()
  private val showRemoveFromFavouritesError = SingleLiveEvent<Any>()
  private val showWordLengthEnabled = SingleLiveEvent<Any>()
  private val random = Random()
  private val generator = WordGenerator()
  private val wordStack = Stack<String>()
  private val wordDao: WordDao = InvitrodeDatabase.getInstance(application).wordDao()
  private var isTwoWords = false

  val getShowSavedToFavourites : LiveData<Any>
    get() = showSavedToFavourites

  val getShowRemovedFromFavourites : LiveData<Any>
    get() = showRemovedFromFavourites

  val getShowSaveToFavouritesError : LiveData<Any>
    get() = showSaveToFavouritesError

  val getShowRemoveFromFavouritesError : LiveData<Any>
    get() = showRemoveFromFavouritesError

  val getShowWordLengthEnabled : LiveData<Any>
    get() = showWordLengthEnabled

  fun onFavouriteButtonClicked() {
    favouriteButtonEnabled.set(false)
    isWordFavourited.set(!isWordFavourited.get())
    if (isWordFavourited.get()) {
      launch(UI) {
        try {
          saveWordToFavorites(Word(word = word.get().toString()))
          showSavedToFavourites.call()
          favouriteButtonEnabled.set(true)
        } catch (e: Exception) {
          showSaveToFavouritesError.call()
        }
      }
    } else {
      launch(UI) {
        try {
          removeWordFromFavorites(word.get().toString())
          showRemovedFromFavourites.call()
          favouriteButtonEnabled.set(true)
        } catch (e: Exception) {
          showRemoveFromFavouritesError.call()
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
      specifyWordLengthEnabled.set(true)
      showWordLengthEnabled.call()
      wordLength.set((value + 3).toString())
    }
  }

  private fun generateRandomWord(): String {
    return when (specifyWordLengthEnabled.get()) {
      true -> {
        val length = wordLength.get()!!.toInt()
        when (isTwoWords) {
          true -> generator.newWord(length) + " " + generator.newWord(length)
          false -> generator.newWord(length)
        }
      }
      false -> {
        when (isTwoWords) {
          true -> generator.newWord(rand(3, 12)) + " " + generator.newWord(rand(3, 12))
          false -> generator.newWord(rand(3, 12))
        }
      }
    }
  }

  private fun resetFavorite() {
    if (isWordFavourited.get()) {
      isWordFavourited.set(false)
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
