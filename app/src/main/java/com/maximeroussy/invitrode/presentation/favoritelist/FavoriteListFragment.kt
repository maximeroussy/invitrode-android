package com.maximeroussy.invitrode.presentation.favoritelist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.maximeroussy.invitrode.R
import com.maximeroussy.invitrode.data.words.Word
import com.maximeroussy.invitrode.databinding.FragmentFavoriteListBinding

class FavoriteListFragment : Fragment(), WordRecyclerViewAdapter.OnItemClickListener {
  private lateinit var binding: FragmentFavoriteListBinding
  private lateinit var viewModel: FavoriteListViewModel
  private lateinit var adapter: WordRecyclerViewAdapter
  private var actionMode: ActionMode? = null

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
    adapter.setOnItemClickListener(this)
    binding.recyclerview.addItemDecoration(DividerItemDecoration(binding.recyclerview.context, VERTICAL))
  }

  private fun subscribeToViewModelEvents() {
    viewModel.getWordList().observe(this, Observer { list -> list?.let { adapter.updateData(list) } })
    viewModel.getShowRemovedFromFavorites.observe(this, Observer {
      Snackbar.make(activity!!.findViewById(R.id.container), R.string.removed_from_favorites, Snackbar.LENGTH_SHORT).show()
    })
  }

  override fun onItemPressed(position: Int) {
    if (adapter.getSelectedItemCount() > 0) {
      select(position)
    } else {
      val word = adapter.getItem(position)
      copySelected(word)
    }
  }

  override fun onItemLongPressed(position: Int) {
    if (actionMode == null) {
      actionMode = (context as AppCompatActivity).startSupportActionMode(ActionModeCallback())
    }
    select(position)
  }

  private fun select(position: Int) {
    adapter.select(position)
    val count = adapter.getSelectedItemCount()
    when (count) {
      0 -> {
        actionMode?.finish()
        actionMode = null
      }
      1 -> {
        setMenuCopyVisibility(true)
        actionMode?.title = count.toString()
        actionMode?.invalidate()
      }
      else -> {
        setMenuCopyVisibility(false)
        actionMode?.title = count.toString()
        actionMode?.invalidate()
      }
    }
  }

  private fun copySelected(value: Word) {
    val clipboard = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText("Copied word", value.word)
    clipboard?.primaryClip = clip
    Snackbar.make(activity!!.findViewById(R.id.container), R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show()
  }

  private fun deleteSelected() {
    viewModel.deleteWords(adapter.getSelectedItems())
    adapter.clearSelections()
    adapter.notifyDataSetChanged()
    actionMode?.finish()
    actionMode = null
  }

  private fun setMenuCopyVisibility(value: Boolean) {
    if (actionMode != null) {
      val menu = actionMode?.menu
      val menuItem = menu?.findItem(R.id.action_copy)
      menuItem?.isVisible = value
    }
  }

  inner class ActionModeCallback : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
      mode.menuInflater.inflate(R.menu.word_action_menu, menu)
      return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
      return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
      when(item.itemId) {
        R.id.action_copy -> {
          val word = adapter.getSelectedItems()[0]
          copySelected(word)
          return true
        }
        R.id.action_delete -> {
          deleteSelected()
          return true
        }
      }
      return false
    }

    override fun onDestroyActionMode(mode: ActionMode) {
      adapter.clearSelections()
      actionMode = null
    }
  }
}
