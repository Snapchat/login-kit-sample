package com.thomasphillips3.goldenfold.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.thomasphillips3.goldenfold.R
import com.thomasphillips3.goldenfold.databinding.FragmentProfileBinding

/**
 * Fragment to display profile info
 */
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        binding.lifecycleOwner = this

        return binding.root
    }
}