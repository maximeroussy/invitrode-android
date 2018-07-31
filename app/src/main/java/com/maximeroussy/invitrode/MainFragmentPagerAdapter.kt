package com.maximeroussy.invitrode

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.maximeroussy.invitrode.favoritelist.FavoriteListFragment
import com.maximeroussy.invitrode.generateword.GenerateWordFragment

class MainFragmentPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
  private val tabTitles = arrayOf("Generator", "Favorites")

  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> GenerateWordFragment.newInstance()
      1 -> FavoriteListFragment.newInstance()
      else -> GenerateWordFragment.newInstance()
    }
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return tabTitles[position]
  }

  override fun getCount(): Int {
    return 2
  }
}
