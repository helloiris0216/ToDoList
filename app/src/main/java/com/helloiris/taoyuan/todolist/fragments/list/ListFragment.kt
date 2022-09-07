package com.helloiris.taoyuan.todolist.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.helloiris.taoyuan.todolist.R
import com.helloiris.taoyuan.todolist.data.viewmodel.ToDoViewModel
import com.helloiris.taoyuan.todolist.fragments.ShareViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val shareViewModel: ShareViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            shareViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        shareViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer { isEmpty ->
            showEmptyView(isEmpty)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // set menu.
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyView(isEmpty: Boolean) {
        var visibility = View.GONE
        if (isEmpty) {
            visibility = View.VISIBLE
        }

        view?.emptyImg?.visibility = visibility
        view?.emptyTv?.visibility = visibility
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
            .setPositiveButton("Yes") {_, _ ->
                toDoViewModel.deleteAll()
                Toast.makeText(requireContext(), "Successfully Removed Everything!'", Toast.LENGTH_SHORT).show()
            }
            .create().show()
    }
}