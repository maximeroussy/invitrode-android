package com.maximeroussy.invitrode.presentation.favoritelist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.databinding.FragmentFavoriteListBinding

class FavoriteListFragment: Fragment() {
  private lateinit var binding: FragmentFavoriteListBinding
  private lateinit var viewModel: FavoriteListViewModel
  private lateinit var adapter: WordRecyclerViewAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_list, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(FavoriteListViewModel::class.java)
    binding.viewModel = viewModel
    configureRecyclerView()
    subscribeToViewModelEvents()
  }

  private fun configureRecyclerView() {
    binding.recyclerview.layoutManager = LinearLayoutManager(context)
    adapter = WordRecyclerViewAdapter(ArrayList())
    binding.recyclerview.adapter = adapter
  }

  private fun subscribeToViewModelEvents() {
    viewModel.getWordList().observe(this, Observer { list -> list?.let { adapter.updateData(list) } })
  }
}
