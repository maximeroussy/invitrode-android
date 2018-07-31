package com.maximeroussy.invitrode

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class MainActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupToolbar()
    setupTabNav()
  }

  private fun setupToolbar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
  }

  private fun setupTabNav() {
    val viewPager = findViewById<ViewPager>(R.id.viewpager)
    val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
    viewPager.adapter = MainFragmentPagerAdapter(supportFragmentManager)
    viewPager.setPageTransformer(true, MainPageTransformer())
    tabLayout.setupWithViewPager(viewPager)
  }
}
