package com.example.android.exam_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    // This MainActivity sets the content view
    // that contains the Navigation Host. It kinda
    // acts like the root div of an SPA.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}