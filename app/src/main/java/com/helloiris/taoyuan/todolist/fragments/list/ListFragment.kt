package com.helloiris.taoyuan.todolist.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.helloiris.taoyuan.todolist.R
import com.helloiris.taoyuan.todolist.data.models.ToDoData
import com.helloiris.taoyuan.todolist.data.viewmodel.ToDoViewModel
import com.helloiris.taoyuan.todolist.databinding.FragmentListBinding
import com.helloiris.taoyuan.todolist.fragments.ShareViewModel
import com.helloiris.taoyuan.todolist.fragments.list.adapter.ListAdapter

class ListFragment : Fragment() {
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val shareViewModel: ShareViewModel by viewModels()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // data binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.shareViewModel = shareViewModel

        // setup recycler view
        setupRecyclerView()

        // observe live data
        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            // update UIs.
            shareViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        // set menu.
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // swipe to delete the item
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object: SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (adapter.dataList.isNotEmpty()) {
                    // delete item
                    val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                    toDoViewModel.deleteData(deletedItem)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)

                    // restore item
                    restoreDeletedData(recyclerView, deletedItem, viewHolder.adapterPosition)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int) {
        Snackbar.make(view, "Deleted ${deletedItem.title}", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                toDoViewModel.insertData(deletedItem)
                adapter.notifyItemChanged(position)
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Everything?")
            .setMessage("Are you sure you want to remove everything")
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                toDoViewModel.deleteAll()
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed Everything!'",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .create().show()
    }
}