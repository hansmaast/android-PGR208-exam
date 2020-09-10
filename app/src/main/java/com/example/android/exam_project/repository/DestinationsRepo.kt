package com.example.android.exam_project.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.exam_project.api.Api
import com.example.android.exam_project.api.toDestinationModelDb
import com.example.android.exam_project.api.toDetailsModelDb
import com.example.android.exam_project.database.DestinationModelDb
import com.example.android.exam_project.database.DetailsModelDb
import com.example.android.exam_project.database.DestinationsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DestinationsRepo(private val database: DestinationsDatabase) {

    // Gets data from the api and inserts it in the database
    suspend fun updateDestinations() {
        withContext(Dispatchers.IO) {
            Log.i("Repo", "Updating destinations on the IO thread!")
            val destinations = Api.webService.getAllDestinationsFromApiAsync().await()
            database.destinationsDao.insertAll(toDestinationModelDb(destinations))
            Log.i("Repo", "Updated!")
        }
    }

    suspend fun updateDestinationDetails(id: String) {
        withContext(Dispatchers.IO) {
            Log.i("Repo", "Updating details on the IO thread!")
            val details = Api.webService.getDetailsFromApiAsync(id).await()
            database.destinationsDao.insertDestinationDetails(toDetailsModelDb(details))
            Log.i("Repo", "Updated!")
        }
    }

    // Calls a query in the Dao and returns the data
    fun getQueryFromDb(query: String): LiveData<List<DestinationModelDb>> {
        // formats the search string to be a wildcard
        val formatQuery = "%${query}%"
        return database.destinationsDao.getDestinationByName(formatQuery)
    }

    fun getDestinationDetails(id: String): LiveData<DetailsModelDb> {
        return database.destinationsDao.getDestinationDetailsFromDb(id)
    }
}