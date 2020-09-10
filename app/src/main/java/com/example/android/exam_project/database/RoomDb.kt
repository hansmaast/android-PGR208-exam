package com.example.android.exam_project.database

import android.content.Context
import androidx.room.*

// Creating a database class
@Database(entities = [DestinationModelDb::class, DetailsModelDb::class], version = 1, exportSchema = false)
abstract class DestinationsDatabase : RoomDatabase() {
    abstract val destinationsDao: DestinationsDao
}

// Holds the singleton database object
private lateinit var INSTANCE: DestinationsDatabase

// Creates and returns an instance of the database
fun getDatabase(context: Context): DestinationsDatabase {
    synchronized(DestinationsDatabase::class.java) {
        // Initializes DB only if NOT already initialized
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DestinationsDatabase::class.java,
                "destinations"
            ).build()
        }
    }
    return INSTANCE
}