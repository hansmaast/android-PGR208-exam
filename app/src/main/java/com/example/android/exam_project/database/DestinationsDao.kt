package com.example.android.exam_project.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao // The Dao contains actions to access and manipulate the database
interface DestinationsDao {

    @Query("select * from destinations order by name asc")
    fun getDestinations(): LiveData<List<DestinationModelDb>>

    // this query contains the param :search
    // which we get from an input in the UI
    @Query("select * from destinations " +
                "where name like :query " +
                "order by name asc "
    ) fun getDestinationByName(query: String?): LiveData<List<DestinationModelDb>>

    @Query("select * from destination_details where id = :id ")
    fun getDestinationDetailsFromDb(id: String): LiveData<DetailsModelDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(destinationModelsDb: List<DestinationModelDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDestinationDetails(detailsModelDb: DetailsModelDb)

}