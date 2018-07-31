package com.maximeroussy.invitrode.generateword

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.databinding.FragmentGenerateWordBinding

class GenerateWordFragment: Fragment() {
  lateinit var binding: FragmentGenerateWordBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate_word, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val viewModel = ViewModelProviders.of(this).get(GenerateWordViewModel::class.java)
    binding.viewModel = viewModel
  }

  companion object {
    fun newInstance(): GenerateWordFragment {
      return GenerateWordFragment()
    }
  }
}
