package com.maximeroussy.invitrodeandroid.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.maximeroussy.invitrodeandroid.presentation.favoritelist.FavouriteListFragment
import com.maximeroussy.invitrodeandroid.presentation.generateword.GenerateWordFragment

class MainFragmentPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
  private val tabTitles = arrayOf("Generator", "Favourites")

  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> GenerateWordFragment()
      1 -> FavouriteListFragment()
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
