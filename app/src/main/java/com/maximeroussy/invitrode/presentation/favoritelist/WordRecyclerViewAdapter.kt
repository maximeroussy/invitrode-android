package com.maximeroussy.invitrode.presentation.favoritelist

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.data.words.Word
import com.maximeroussy.invitrode.util.ViewHolder

class WordRecyclerViewAdapter(private val items: MutableList<Word>) : RecyclerView.Adapter<ViewHolder>() {
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = items[position]
    holder.bind(item as Any)
  }

  override fun getItemCount(): Int = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.view_word_list_item, parent, false)
    return ViewHolder(binding)
  }

  fun updateData(newData: List<Word>) {
    this.items.clear()
    this.items.addAll(newData)
    notifyDataSetChanged()
  }
}
