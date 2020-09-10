package com.example.android.exam_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.exam_project.database.DestinationModelDb
import com.example.android.exam_project.databinding.FragmentDestinationsBinding
import com.example.android.exam_project.utils.DestinationListAdapter
import com.example.android.exam_project.viewModels.DestinationsViewModel

class DestinationsFragment : Fragment() {

    private val viewModelDestinations: DestinationsViewModel by viewModels {
        DestinationsViewModel.Factory(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        // Reference to the binding and inflates the layout
        val binding = FragmentDestinationsBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ViewModel
        binding.viewModel = viewModelDestinations

        // Sets the adapter for the RecyclerView
        val adapter = DestinationListAdapter()
        binding.destinationList.adapter = adapter

        // Getting all results with empty String
        updateRecyclerView("", adapter, binding)

        // Adds a QueryTextListener to the searchTextInput
        binding.searchTextInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String): Boolean {
                updateRecyclerView(newText.trim(), adapter, binding)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                // filters the recyclerview based on the value from the searchTextInput
                updateRecyclerView(newText.trim(), adapter, binding)
                return false
            }
        })

        return binding.root
    }
    
    
    private fun updateRecyclerView(
        searchString: String,
        adapter: DestinationListAdapter,
        binding: FragmentDestinationsBinding
    ) {
        // Updates the recycler view when query result changes
        viewModelDestinations.getQueryResult(searchString).observe(viewLifecycleOwner, Observer { result ->
            displayOrHideProgressBar(result, binding)
            adapter.submitList(result)
        })
    }
    
    private fun displayOrHideProgressBar(
        result: List<DestinationModelDb>,
        binding: FragmentDestinationsBinding
    ) {
        if (result.isEmpty()) {
            binding.destinationList.visibility = View.GONE
        } else if (result.isNotEmpty()) {
            binding.destinationList.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}