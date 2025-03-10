package com.arturo.movies.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.content.pm.PackageManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.arturo.movies.MainActivity
import com.arturo.movies.R
import com.google.android.gms.location.*
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var firestore: FirebaseFirestore
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        firestore = FirebaseFirestore.getInstance()

        createNotificationChannel()
        startForeground(1, createForegroundNotification())

        setupLocationUpdates()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "LOCATION_SERVICE_CHANNEL", // ID del canal (debe coincidir con el usado en la notificación)
                "Servicio de Ubicación", // Nombre visible en Configuración
                NotificationManager.IMPORTANCE_LOW // IMPORTANCE_LOW para evitar alertas innecesarias
            ).apply {
                description = "Notificaciones del servicio de ubicación en segundo plano"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun createForegroundNotification(): Notification {
        val notification = NotificationCompat.Builder(this, "LOCATION_SERVICE_CHANNEL")
            .setContentTitle("Servicio de Ubicación Activo")
            .setContentText("Recopilando datos de ubicación en segundo plano")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        return notification
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationUpdates() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5 * 60 * 1000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5 * 60 * 1000)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d("LocationService", "Ubicación obtenida: ${location.latitude}, ${location.longitude}")
                    saveLocationToFirestore(location)
                } ?: Log.e("LocationService", "No se recibió ninguna ubicación")
            }
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    Log.d("LocationService", "Ubicación inicial obtenida: ${location.latitude}, ${location.longitude}")
                    saveLocationToFirestore(location)
                } else {
                    Log.e("LocationService", "No se pudo obtener la ubicación inicial")
                }
            }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
    }

    private fun saveLocationToFirestore(location: Location) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()) // Formato para ID único basado en timestamp
        val locationData = hashMapOf(
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "timestamp" to timestamp
        )

        firestore.collection("user_locations")
            .document(timestamp) // Usar timestamp como ID del documento
            .set(locationData)
            .addOnSuccessListener {
                Log.d("LocationService", "Ubicación guardada con ID: $timestamp")
                sendLocationUpdateNotification(location, timestamp)
            }
            .addOnFailureListener {
                Log.e("LocationService", "Error al guardar la ubicación", it)
            }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun sendLocationUpdateNotification(location: Location, timestamp: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(this, "LOCATION_SERVICE_CHANNEL")
            .setContentTitle("Ubicación actualizada")
            .setContentText("Lat: ${location.latitude}, Lng: ${location.longitude} a las $timestamp")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify((System.currentTimeMillis() % 10000).toInt(), notification)
    }
}