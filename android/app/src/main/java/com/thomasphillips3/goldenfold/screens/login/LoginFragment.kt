package com.thomasphillips3.goldenfold.screens.login

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.snapchat.kit.sdk.SnapLogin
import com.snapchat.kit.sdk.core.controller.LoginStateController
import com.thomasphillips3.goldenfold.R
import com.thomasphillips3.goldenfold.databinding.FragmentLoginBinding
import com.thomasphillips3.goldenfold.databinding.FragmentProfileBinding
import com.thomasphillips3.goldenfold.screens.profile.ProfileFragment


class LoginFragment : Fragment() {

    private var mLoginStateChangedListener: LoginStateController.OnLoginStateChangedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false)

        binding.loginButton.setOnClickListener {
            SnapLogin.getAuthTokenManager(context)
                .startTokenGrant()

            SnapLogin.getLoginStateController(context)
                .addOnLoginStateChangedListener(mLoginStateChangedListener)
        }

        binding.logoutButton.setOnClickListener {
            if (SnapLogin.getAuthTokenManager(context).isUserLoggedIn) {
                SnapLogin.getAuthTokenManager(context)
                    .clearToken()
                binding.logoutButton.visibility = View.GONE
            }
        }

        mLoginStateChangedListener = object :
            LoginStateController.OnLoginStateChangedListener {
            override fun onLoginSucceeded() {
                binding.logoutButton.visibility = View.VISIBLE
                binding.loginButton.visibility = View.GONE
            }

            override fun onLoginFailed() {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }

            override fun onLogout() {
                binding.logoutButton.visibility = View.GONE
                binding.loginButton.visibility = View.VISIBLE
            }
        }
        return binding.root
    }
}