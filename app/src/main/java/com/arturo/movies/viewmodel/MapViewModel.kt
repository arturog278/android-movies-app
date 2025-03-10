package com.arturo.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arturo.movies.model.LocationData
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _locations = MutableLiveData<List<LocationData>>()
    val locations: LiveData<List<LocationData>> = _locations

    fun loadLocationsFromFirestore() {
        firestore.collection("user_locations")
            .get()
            .addOnSuccessListener { documents ->
                val locationList = mutableListOf<LocationData>()
                for (document in documents) {
                    val lat = document.getDouble("latitude") ?: 0.0
                    val lng = document.getDouble("longitude") ?: 0.0
                    val timestamp = document.getString("timestamp") ?: "Sin fecha"
                    locationList.add(LocationData(lat, lng, timestamp))
                }
                _locations.postValue(locationList)
            }
            .addOnFailureListener {
                // Manejar error en la carga de ubicaciones
            }
    }
}