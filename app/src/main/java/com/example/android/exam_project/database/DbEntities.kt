package com.example.android.exam_project.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class DestinationModelDb constructor(
    @PrimaryKey
    val id: String,
    val name: String,
    val latitude: String,
    val longitude: String
)

@Entity(tableName = "destination_details")
data class DetailsModelDb(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo(name = "banner_url")
    val bannerUrl: String,
    val latitude: String,
    val longitude: String,
    val comments: String,
    @ColumnInfo(name = "added_by")
    val addedBy: String
)