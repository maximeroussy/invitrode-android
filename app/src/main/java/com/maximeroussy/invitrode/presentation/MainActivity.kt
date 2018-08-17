package com.maximeroussy.invitrode.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
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
    viewPager.setPageTransformer(true, MainPageTransformer())
    tabLayout.setupWithViewPager(viewPager)
  }

  companion object {
    fun newInstance(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }
}
