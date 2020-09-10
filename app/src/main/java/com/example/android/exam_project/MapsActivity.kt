package com.example.android.exam_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lat : Double = 0.0
    private var lon : Double = 0.0
    private var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        lat = intent.getStringExtra("lat").toBigDecimal().toDouble()
        lon = intent.getStringExtra("lon").toBigDecimal().toDouble()
        name = intent.getStringExtra("name")

        this.supportActionBar?.title = name
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latAndLon = LatLng(lat, lon)

        mMap.apply {
            addMarker(MarkerOptions().position(latAndLon).title("Marker in ${name}"))
            moveCamera(CameraUpdateFactory.newLatLngZoom(latAndLon, 5f))
        }

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
        }
    }
}
