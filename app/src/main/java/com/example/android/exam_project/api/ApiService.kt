package com.example.android.exam_project.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.noforeignland.com/home/api/v1/"

// Using moshi to parse the JSON
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Using retrofit && moshi to build a web service API
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Allows us to replace the Call object with a Deferred Object
    .baseUrl(BASE_URL)
    .build()

// Defining an interface that communicates with the web server
interface ApiService {
    @GET("places")
    fun getAllDestinationsFromApiAsync():
            // Returns a list of DestinationData
            Deferred<Destinations> // "Deferred" is non-blocking

    @GET("place?")
    fun getDetailsFromApiAsync(@Query("id") id: String):
    // Returns a list of DestinationData
            Deferred<Details> // "Deferred" is non-blocking
}

// Creating a public object to initialize the Retrofit (web) service
object Api {
    val webService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}