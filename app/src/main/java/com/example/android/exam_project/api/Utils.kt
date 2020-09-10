package com.example.android.exam_project.api

import android.os.Build
import android.text.Html
import com.example.android.exam_project.database.DestinationModelDb
import com.example.android.exam_project.database.DetailsModelDb

// Since it is difficult to store nested objects in SQL,
// we use these functions to map each DTO to a
// new data class with a flat data structure.
fun toDestinationModelDb(src: Destinations): List<DestinationModelDb> {
    return src.destinations.map {
        DestinationModelDb(
            name = it.properties.name,
            id = it.properties.id.toBigDecimal().toPlainString(),
            latitude = it.geometry.coordinates[1].toBigDecimal().toPlainString(),
            longitude = it.geometry.coordinates[0].toBigDecimal().toPlainString()
        )
    }
}

fun toDetailsModelDb(src: Details): DetailsModelDb {
    return DetailsModelDb(
        id = src.place.id.toBigDecimal().toPlainString(),
        name = src.place.name,
        bannerUrl = src.place.bannerUrl,
        addedBy = src.place.addedBy,
        latitude = src.place.lat.toBigDecimal().toPlainString(),
        longitude = src.place.lon.toBigDecimal().toPlainString(),
        comments = fromHtmlToString(src.place.comments)
    )
}

// Formatting the html to a text string
@Suppress("DEPRECATION")
private fun fromHtmlToString(src: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            src,
            Html.FROM_HTML_MODE_LEGACY
        ).toString()
    } else {
        Html.fromHtml(src).toString()
    }
}