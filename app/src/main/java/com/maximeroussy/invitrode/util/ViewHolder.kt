package com.maximeroussy.invitrode.util

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.maximeroussy.invitrode.BR

class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(item: Any) {
    binding.setVariable(BR.item, item)
    binding.executePendingBindings()
  }
}
