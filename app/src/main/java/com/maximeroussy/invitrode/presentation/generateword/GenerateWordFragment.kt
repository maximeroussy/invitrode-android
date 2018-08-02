package com.maximeroussy.invitrode.presentation.generateword

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.data.InvitrodeDatabase
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
    setupCheckbox()
    viewModel.bound(InvitrodeDatabase.getInstance(context!!))
  }

  private fun setupCheckbox() {
    binding.specifyLengthCheckbox.setOnClickListener { view ->
      viewModel.onSpecifyWordLengthOptionChanged((view as CheckBox).isChecked)
    }
  }
}
