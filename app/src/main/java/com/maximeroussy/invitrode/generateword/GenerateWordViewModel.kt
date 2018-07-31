package com.maximeroussy.invitrode.generateword

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.maximeroussy.invitrode.RandomWord
import java.util.Random

class GenerateWordViewModel: ViewModel() {
  val generatedWord = ObservableField<String>("invitrode")
  private val random = Random()

  fun generateNewWord() {
    val newWord = RandomWord.getNewWord(rand(3, 20))
    generatedWord.set(newWord)
  }

  private fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
  }
}
