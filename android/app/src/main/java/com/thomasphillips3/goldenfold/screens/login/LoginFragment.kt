package com.thomasphillips3.goldenfold.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.snapchat.kit.sdk.SnapLogin
import com.snapchat.kit.sdk.core.controller.LoginStateController
import com.thomasphillips3.goldenfold.R
import com.thomasphillips3.goldenfold.databinding.FragmentLoginBinding


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

        mLoginStateChangedListener = object :
            LoginStateController.OnLoginStateChangedListener {
            override fun onLoginSucceeded() {
                // Here you could update UI to show login success
            }

            override fun onLoginFailed() {
                // Here you could update UI to show login failure
            }

            override fun onLogout() {
                // Here you could update UI to reflect logged out state
            }
        }
        return binding.root
    }
}