package com.arturo.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arturo.movies.databinding.ItemLocationBinding
import com.arturo.movies.model.LocationData

class LocationAdapter(
    private val locations: List<LocationData>,
    private val onLocationClick: (LocationData) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int = locations.size

    inner class LocationViewHolder(private val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: LocationData) {
            binding.locationTimestamp.text = "Fecha y Hora: ${location.timestamp}"
            binding.locationCoordinates.text = "Coordenadas: ${location.latitude}, ${location.longitude}"
            binding.root.setOnClickListener { onLocationClick(location) }
        }
    }
}