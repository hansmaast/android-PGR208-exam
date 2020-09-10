package com.example.android.exam_project.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.android.exam_project.MapsActivity
import com.example.android.exam_project.databinding.FragmentDestinationDetailsBinding
import com.example.android.exam_project.viewModels.DetailsViewModel


class DetailsFragment : Fragment() {

    private val detailsArgs: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Factory(
            // passing in application context to the viewModel
            requireActivity().application,
            // Passing in arguments  (for accessing ID)
            detailsArgs
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDestinationDetailsBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.details.observe(viewLifecycleOwner, Observer { details ->
            if (details == null)
                binding.apply {
                    writtenBy.visibility = View.GONE
                }
            else
                binding.apply {
                    binding.progressBarDetails.visibility = View.GONE
                    writtenBy.visibility = View.VISIBLE
                }
        })

        binding.floatingMapButton.setOnClickListener {
            openInMaps()
        }

        return binding.root
    }

    private fun openInMaps() {
        val intent = Intent(context, MapsActivity::class.java)

        val lat = viewModel.details.value?.latitude
        val lon = viewModel.details.value?.longitude
        val name = viewModel.details.value?.name?.trim()

        intent.putExtra("lat", lat)
        intent.putExtra("lon", lon)
        intent.putExtra("name", name)
        startActivity(intent)
    }
}