package com.maximeroussy.invitrode.presentation.generateword

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.databinding.FragmentGenerateWordBinding


class GenerateWordFragment: Fragment() {
  private lateinit var binding: FragmentGenerateWordBinding
  private lateinit var viewModel: GenerateWordViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate_word, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(GenerateWordViewModel::class.java)
    binding.viewModel = viewModel
    setupCheckboxes()
    setupObservables()
  }

  private fun setupCheckboxes() {
    binding.specifyLengthCheckbox.setOnClickListener { view ->
      viewModel.onSpecifyWordLengthOptionChanged((view as CheckBox).isChecked)
    }
    binding.twoWordsCheckbox.setOnClickListener { view ->
      viewModel.onTwoWordsOptionChanged((view as CheckBox).isChecked)
    }
  }

  private fun setupObservables() {
    viewModel.getShowSavedToFavorites.observe(this, Observer {
      Snackbar.make(activity!!.findViewById(R.id.container), R.string.saved_to_favorites, Snackbar.LENGTH_SHORT).show()
    })
    viewModel.getShowRemovedFromFavorites.observe(this, Observer {
      Snackbar.make(activity!!.findViewById(R.id.container), R.string.removed_from_favorites, Snackbar.LENGTH_SHORT).show()
    })
  }
}
