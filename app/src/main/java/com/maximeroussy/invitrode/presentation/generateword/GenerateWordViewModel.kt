package com.maximeroussy.invitrode.presentation.generateword

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.maximeroussy.invitrode.RandomWord
import com.maximeroussy.invitrode.data.InvitrodeDatabase
import com.maximeroussy.invitrode.data.words.Word
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.Random

class GenerateWordViewModel: ViewModel() {
  val generatedWord = ObservableField<String>("INVITRODE")
  val wordLength = ObservableField<String>("3")
  val generateButtonEnabled = ObservableBoolean(true)
  val specifyWordLengthEnabled = ObservableBoolean(false)
  val isWordFavorited = ObservableBoolean(false)
  private val random = Random()
  private lateinit var database: InvitrodeDatabase

  fun bound(database: InvitrodeDatabase) {
    this.database = database
  }

  fun onFavoriteClicked() {
    isWordFavorited.set(!isWordFavorited.get())
    if (isWordFavorited.get()) {
      launch(UI) {
        saveWordToFavorites(Word(word = generatedWord.get().toString()))
      }
    }
  }

  fun generateNewWord() {
    val newWord = when(specifyWordLengthEnabled.get()) {
      true -> RandomWord.getNewWord(wordLength.get()!!.toInt())
      false -> RandomWord.getNewWord(rand(3, 12))
    }
    if (isWordFavorited.get()) {
      isWordFavorited.set(false)
    }
    generatedWord.set(newWord)
  }

  fun onSpecifyWordLengthOptionChanged(value: Boolean) {
    specifyWordLengthEnabled.set(value)
  }

  fun onWordLengthChanged(value: Int, fromUser: Boolean) {
    if (fromUser) {
      // minimum word length is enforced to 3
      wordLength.set((value + 3).toString())
    }
  }

  private suspend fun saveWordToFavorites(word: Word) = withContext(CommonPool) {
    database.wordDao().insert(word)
  }

  private fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
  }
}
