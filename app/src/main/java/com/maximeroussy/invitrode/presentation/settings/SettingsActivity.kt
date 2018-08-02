package com.maximeroussy.invitrode.presentation.settings

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.databinding.ActivitySettingsBinding

class SettingsActivity: AppCompatActivity() {
  private lateinit var binding: ActivitySettingsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
    setupToolbar()
  }

  private fun setupToolbar() {
    setSupportActionBar(binding.toolbar)
    supportActionBar?.let {
      it.setDisplayHomeAsUpEnabled(true)
      it.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    fun newInstance(context: Context): Intent {
      return Intent(context, SettingsActivity::class.java)
    }
  }
}
