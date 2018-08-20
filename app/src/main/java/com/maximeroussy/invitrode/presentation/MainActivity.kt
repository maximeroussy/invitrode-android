package com.maximeroussy.invitrode.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.maximeroussy.invitrode.R.id
import com.maximeroussy.invitrode.R.layout

class MainActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    setupToolbar()
    setupTabNav()
  }

  private fun setupToolbar() {
    val toolbar = findViewById<Toolbar>(id.toolbar)
    setSupportActionBar(toolbar)
  }

  private fun setupTabNav() {
    val viewPager = findViewById<ViewPager>(id.viewpager)
    val tabLayout = findViewById<TabLayout>(id.tab_layout)
    viewPager.adapter = MainFragmentPagerAdapter(supportFragmentManager)
    tabLayout.setupWithViewPager(viewPager)
  }
}
