package com.maximeroussy.invitrode.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    startActivity(MainActivity.newInstance(this))
    finish()
  }
}
