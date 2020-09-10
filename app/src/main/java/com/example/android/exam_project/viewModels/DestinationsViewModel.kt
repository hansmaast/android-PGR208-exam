package com.example.android.exam_project.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.exam_project.repository.DestinationsRepo
import kotlinx.coroutines.*
import com.example.android.exam_project.database.DestinationModelDb
import com.example.android.exam_project.database.getDatabase
import java.io.IOException

// Using AndroidViewModel to get the applications context
class DestinationsViewModel(application: Application) : AndroidViewModel(application) {

    // gets the repo an initializes det DB
    private val destinationsRepo = DestinationsRepo(getDatabase(application))

    fun getQueryResult(q: String): LiveData<List<DestinationModelDb>> {
        return destinationsRepo.getQueryFromDb(q)
    }

    // This is the job for all coroutines started by this ViewModel.
    // Cancelling this job will cancel all coroutines started by this ViewModel.
    private val viewModelJob = SupervisorJob()

    //This is the main scope for all coroutines launched by  this ViewModel.
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Log.i("VM", "Init called!")
        refreshDataFromRepository()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                destinationsRepo.updateDestinations()
            } catch (networkError: IOException) {
                if (getQueryResult("").value.isNullOrEmpty()) {
                    Log.e("Error: ", networkError.message)
                }
            }
        }
    }

    // Cancel all coroutines when the ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Factory for constructing ViewModel with parameter (application context)
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DestinationsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DestinationsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}