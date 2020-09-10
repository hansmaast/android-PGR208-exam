package com.example.android.exam_project.api

import com.squareup.moshi.Json

data class Destinations(
    @Json(name = "features")
    val destinations: List<Destination>
)

data class Destination(
    val properties: Properties,
    val geometry: Geometry
)

data class Properties(
    val name: String,
    val id: Double
)

data class Geometry(
    val coordinates: List<Double>
)

data class Details(
    val place: Place
)

data class Place(
    val id: Double,
    val name: String,
    @Json(name = "banner")
    val bannerUrl: String,
    val lat: Double,
    val lon: Double,
    val comments: String,
    val addedBy: String
)