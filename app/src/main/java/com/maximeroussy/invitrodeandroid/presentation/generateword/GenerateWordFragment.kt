package com.maximeroussy.invitrodeandroid.presentation.generateword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.maximeroussy.invitrodeandroid.R
import com.maximeroussy.invitrodeandroid.R.string
import com.maximeroussy.invitrodeandroid.databinding.FragmentGenerateWordBinding

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
      Snackbar.make(activity!!.findViewById(R.id.container), string.saved_to_favorites, Snackbar.LENGTH_SHORT).show()
    })
    viewModel.getShowRemovedFromFavorites.observe(this, Observer {
      Snackbar.make(activity!!.findViewById(R.id.container), R.string.removed_from_favorites, Snackbar.LENGTH_SHORT).show()
    })
    viewModel.getShowSaveToFavoritesError.observe(this, Observer {
      showDialog(R.string.error_saving_to_favorites)
    })
    viewModel.getShowRemoveFromFavoritesError.observe(this, Observer {
      showDialog(R.string.error_removing_from_favorites)
    })
    viewModel.getShowWordLengthEnabled.observe(this, Observer {
      binding.specifyLengthCheckbox.isChecked = true
    })
  }

  private fun showDialog(messageId: Int) {
    activity?.let {
      val builder = AlertDialog.Builder(it)
      builder.setTitle(R.string.error)
      builder.setMessage(messageId)
      builder.setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
      builder.show()
    }
  }
}
