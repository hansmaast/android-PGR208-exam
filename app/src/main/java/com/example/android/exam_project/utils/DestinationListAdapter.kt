/*
* This code is tweaked from following codelab:
* https://codelabs.developers.google.com/codelabs/kotlin-android-training-internet-images/#3
*/

package com.example.android.exam_project.utils

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exam_project.MapsActivity
import com.example.android.exam_project.database.DestinationModelDb
import com.example.android.exam_project.databinding.ListViewDestinationBinding
import com.example.android.exam_project.fragments.DestinationsFragmentDirections
import kotlinx.android.synthetic.main.list_view_destination.view.*

class DestinationListAdapter : ListAdapter<
        DestinationModelDb,
        DestinationListAdapter.ViewHolder
        >(DestinationDiffCallback()) {

    class ViewHolder private constructor(
        private var binding: ListViewDestinationBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(destinationModelDb: DestinationModelDb) {
            binding.destination = destinationModelDb
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListViewDestinationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destination = getItem(position)
        holder.bind(destination)
        holder.itemView.apply {
            // Click listeners on card
            cardContainer.setOnClickListener {
                Log.i("Clicked ID", destination.id)
                // Gets the id of the selected destination
                val id = destination.id
                // Defines the action for navigating to the details fragment
                val action =
                    DestinationsFragmentDirections
                        // passing in the id as an argument to the fragment
                        .actionDestinationsFragmentToDetailsFragment(id)
                // navigates with the selected action
                it.findNavController().navigate(action)
            }
            // Click listeners on map icon
            listIcon.setOnClickListener {

                val intent = Intent(context, MapsActivity::class.java)
                intent.putExtra("lat", destination.latitude)
                intent.putExtra("lon", destination.longitude)
                intent.putExtra("name", destination.name.trim())
                context.startActivity(intent)

            }
        }
    }
}

class DestinationDiffCallback : DiffUtil.ItemCallback<DestinationModelDb>() {
    override fun areItemsTheSame(
        oldItem: DestinationModelDb,
        newItem: DestinationModelDb
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DestinationModelDb,
        newItem: DestinationModelDb
    ): Boolean {
        return oldItem == newItem
    }
}