package com.maximeroussy.invitrode.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashScreenActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    startActivity(MainActivity.newInstance(this))
    finish()
  }
}
