package com.arturo.movies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arturo.movies.R
import com.arturo.movies.databinding.FragmentProfileBinding
import com.arturo.movies.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.user.observe(viewLifecycleOwner) { user ->
            binding.userName.text = user?.name
            Glide.with(this)
                .load(user?.avatarPath)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.userAvatar)
        }

        userViewModel.fetchUser(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}