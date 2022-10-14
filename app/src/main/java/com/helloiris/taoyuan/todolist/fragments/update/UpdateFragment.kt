package com.helloiris.taoyuan.todolist.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.helloiris.taoyuan.todolist.R
import com.helloiris.taoyuan.todolist.data.models.Priority
import com.helloiris.taoyuan.todolist.data.models.ToDoData
import com.helloiris.taoyuan.todolist.data.viewmodel.ToDoViewModel
import com.helloiris.taoyuan.todolist.databinding.FragmentUpdateBinding
import com.helloiris.taoyuan.todolist.fragments.ShareViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val shareViewModel: ShareViewModel by viewModels()
    private val todoViewModel: ToDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // data binding
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.currentTitleEd.setText(args.currentItem.title)
        binding.currentDescriptionEd.setText(args.currentItem.description)
        binding.currentPrioritiesSpinner.setSelection(shareViewModel.parsePriorityToInt(args.currentItem.priority))
        binding.currentPrioritiesSpinner.onItemSelectedListener = shareViewModel.listener

        // set menu
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = currentTitleEd.text.toString()
        val description = currentDescriptionEd.text.toString()
        val priority = currentPrioritiesSpinner.selectedItem.toString()

        val validation = shareViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updateItem = ToDoData(
                args.currentItem.id,
                title,
                shareViewModel.parsePriority(priority),
                description
            )

            todoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()

            // navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${args.currentItem.title}' ?")
            .setMessage("Are you sure you want to remove '${args.currentItem.title}")
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") {_, _ ->
                todoViewModel.deleteData(args.currentItem)
                Toast.makeText(requireContext(), "Successfully Removed '${args.currentItem.title}'", Toast.LENGTH_SHORT).show()

                // navigate back
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            .create().show()
    }
}