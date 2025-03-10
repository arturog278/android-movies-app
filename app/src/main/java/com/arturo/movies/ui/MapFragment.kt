package com.arturo.movies.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arturo.movies.databinding.FragmentMapBinding
import com.arturo.movies.model.LocationData
import com.arturo.movies.ui.adapters.LocationAdapter
import com.arturo.movies.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val mapViewModel: MapViewModel by viewModels()
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationAdapter = LocationAdapter(emptyList()) { location ->
            val intent = Intent(requireContext(), LocationDetailActivity::class.java).apply {
                putExtra("latitude", location.latitude)
                putExtra("longitude", location.longitude)
                putExtra("timestamp", location.timestamp)
            }
            startActivity(intent)
        }

        binding.recyclerViewLocations.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationAdapter
        }

        mapViewModel.locations.observe(viewLifecycleOwner) { locations ->
            locationAdapter = LocationAdapter(locations) { location ->
                val intent = Intent(requireContext(), LocationDetailActivity::class.java).apply {
                    putExtra("latitude", location.latitude)
                    putExtra("longitude", location.longitude)
                    putExtra("timestamp", location.timestamp)
                }
                startActivity(intent)
            }
            binding.recyclerViewLocations.adapter = locationAdapter
        }

        mapViewModel.loadLocationsFromFirestore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}