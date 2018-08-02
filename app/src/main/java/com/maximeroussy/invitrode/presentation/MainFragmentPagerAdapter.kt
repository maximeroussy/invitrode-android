package com.maximeroussy.invitrode.presentation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.maximeroussy.invitrode.presentation.favoritelist.FavoriteListFragment
import com.maximeroussy.invitrode.presentation.generateword.GenerateWordFragment

class MainFragmentPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
  private val tabTitles = arrayOf("Generator", "Favorites")

  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> GenerateWordFragment()
      1 -> FavoriteListFragment()
      else -> GenerateWordFragment()
    }
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return tabTitles[position]
  }

  override fun getCount(): Int {
    return 2
  }
}
