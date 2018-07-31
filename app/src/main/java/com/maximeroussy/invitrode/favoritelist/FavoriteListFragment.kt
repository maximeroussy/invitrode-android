package com.maximeroussy.invitrode.favoritelist

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.databinding.FragmentFavoriteListBinding

class FavoriteListFragment: Fragment() {
  lateinit var binding: FragmentFavoriteListBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_list, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val viewModel = ViewModelProviders.of(this).get(FavoriteListViewModel::class.java)
    binding.viewModel = viewModel
  }

  companion object {
    fun newInstance(): FavoriteListFragment {
      return FavoriteListFragment()
    }
  }
}
