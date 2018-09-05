package com.maximeroussy.invitrodeandroid.presentation.favoritelist

import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.maximeroussy.invitrodeandroid.BR
import com.maximeroussy.invitrodeandroid.R
import com.maximeroussy.invitrodeandroid.data.words.Word
import com.maximeroussy.invitrodeandroid.presentation.favoritelist.WordRecyclerViewAdapter.WordViewHolder

class WordRecyclerViewAdapter(private val items: MutableList<Word>) : RecyclerView.Adapter<WordViewHolder>() {
  private var onItemClickListener: OnItemClickListener? = null
  private val selectedItems = SparseBooleanArray()

  interface OnItemClickListener {
    fun onItemPressed(position: Int)
    fun onItemLongPressed(position: Int)
  }

  override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
    val item = items[position]
    holder.bind(item as Any)
    holder.itemView.setBackgroundColor(if (selectedItems.get(position, false)) Color.GRAY else Color.WHITE )
  }

  override fun getItemCount(): Int = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.view_word_list_item, parent, false)
    return WordViewHolder(binding)
  }

  fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
    this.onItemClickListener = onItemClickListener
  }

  fun select(position: Int) {
    if (selectedItems.get(position, false)) {
      selectedItems.delete(position)
    } else {
      selectedItems.put(position, true)
    }
    notifyItemChanged(position)
  }

  fun clearSelections() {
    selectedItems.clear()
    notifyDataSetChanged()
  }

  fun getSelectedItemCount(): Int {
    return selectedItems.size()
  }

  fun getSelectedItems(): List<Word> {
    val selectedWords = ArrayList<Word>()
    for(i in items.indices) {
      if (selectedItems.get(i, false)) {
        selectedWords.add(items[i])
      }
    }
    return selectedWords
  }

  fun getItem(position: Int): Word {
    return items[position]
  }

  fun updateData(newData: List<Word>) {
    this.items.clear()
    this.items.addAll(newData)
    notifyDataSetChanged()
  }

  inner class WordViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root),
      View.OnClickListener, View.OnLongClickListener {
    fun bind(item: Any) {
      binding.setVariable(BR.item, item)
      binding.executePendingBindings()
      binding.root.setOnClickListener(this)
      binding.root.setOnLongClickListener(this)
    }

    override fun onClick(view: View) {
      if (onItemClickListener != null) {
        onItemClickListener!!.onItemPressed(adapterPosition)
      }
    }

    override fun onLongClick(view: View): Boolean {
      if (onItemClickListener != null) {
        onItemClickListener!!.onItemLongPressed(adapterPosition)
      }
      return true
    }
  }
}
