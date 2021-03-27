package com.thomasphillips3.goldenfold.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.snapchat.kit.sdk.SnapLogin
import com.thomasphillips3.goldenfold.R
import com.thomasphillips3.goldenfold.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.loginButton.setOnClickListener {
            SnapLogin.getAuthTokenManager(context).startTokenGrant()
        }
        return binding.root
    }
}