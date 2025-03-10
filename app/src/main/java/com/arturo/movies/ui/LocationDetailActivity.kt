package com.arturo.movies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arturo.movies.R
import com.arturo.movies.databinding.ActivityLocationDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityLocationDetailBinding
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var timestamp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el botón de retroceso en la Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalle de Ubicación"

        // Obtener datos de la intención
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
        timestamp = intent.getStringExtra("timestamp") ?: "Sin fecha"

        // Configurar el mapa
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Mostrar los datos en la UI
        binding.locationInfo.text =
            "Ubicación registrada el: $timestamp\nCoordenadas: $latitude, $longitude"
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("Ubicación registrada"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}