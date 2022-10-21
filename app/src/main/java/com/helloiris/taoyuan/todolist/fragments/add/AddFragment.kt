package com.helloiris.taoyuan.todolist.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.helloiris.taoyuan.todolist.R
import com.helloiris.taoyuan.todolist.data.models.ToDoData
import com.helloiris.taoyuan.todolist.data.viewmodel.ToDoViewModel
import com.helloiris.taoyuan.todolist.fragments.ShareViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val shareViewModel: ShareViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // set menu
        setHasOptionsMenu(true)

        // set the selected listener
        view.prioritiesSpinner.onItemSelectedListener = shareViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title = titleEd.text.toString()
        val priority = prioritiesSpinner.selectedItem.toString()
        val description = descriptionEd.text.toString()

        val validation = shareViewModel.verifyDataFromUser(title, description)
        if (validation) {
            // insert data to database
            val  newData = ToDoData(
                0,
                title,
                shareViewModel.parsePriority(priority),
                description
            )

            toDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            // navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        }
    }
}