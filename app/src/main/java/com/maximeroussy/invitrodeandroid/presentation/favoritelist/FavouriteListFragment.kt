package com.maximeroussy.invitrodeandroid.presentation.favoritelist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.maximeroussy.invitrodeandroid.R
import com.maximeroussy.invitrodeandroid.data.words.Word
import com.maximeroussy.invitrodeandroid.databinding.FragmentFavouriteListBinding

class FavouriteListFragment : Fragment(), WordRecyclerViewAdapter.OnItemClickListener {
  private lateinit var binding: FragmentFavouriteListBinding
  private lateinit var viewModel: FavouriteListViewModel
  private lateinit var adapter: WordRecyclerViewAdapter
  private var actionMode: ActionMode? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_list, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(FavouriteListViewModel::class.java)
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
    viewModel.getShowRemoveFromFavoritesError.observe(this, Observer {
      showDialog(R.string.error_removing_from_favorites)
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

  private fun showDialog(messageId: Int) {
    activity?.let {
      val builder = AlertDialog.Builder(it)
      builder.setTitle(R.string.error)
      builder.setMessage(messageId)
      builder.setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
      builder.show()
    }
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
