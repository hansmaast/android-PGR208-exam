package com.example.android.exam_project.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.exam_project.database.getDatabase
import com.example.android.exam_project.fragments.DetailsFragmentArgs
import com.example.android.exam_project.repository.DestinationsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

// Using AndroidViewModel to get the applications context
class DetailsViewModel(
    application: Application,
    detailsArgs: DetailsFragmentArgs
) : AndroidViewModel(application) {

    private val destinationsRepo = DestinationsRepo(getDatabase(application))

    private var destinationId = detailsArgs.destinationID

    var details= destinationsRepo.getDestinationDetails(destinationId)

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        refreshDetailsFromRepository(destinationId)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun refreshDetailsFromRepository(id: String) {
        viewModelScope.launch {
            try {
                destinationsRepo.updateDestinationDetails(id)
            } catch (networkError: IOException) {
                if (details.value == null) {
                    Log.e("Error: ", networkError.message)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Factory for constructing ViewModel with parameter (application context)
    class Factory(
        val app: Application,
        private val detailsFragmentArgs: DetailsFragmentArgs
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(app, detailsFragmentArgs) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}