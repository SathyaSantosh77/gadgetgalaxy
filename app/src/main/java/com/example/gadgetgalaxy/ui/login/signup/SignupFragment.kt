package com.example.gadgetgalaxy.ui.login.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gadgetgalaxy.common.invisible
import com.example.gadgetgalaxy.common.isValidEmail
import com.example.gadgetgalaxy.common.isValidPassword
import com.example.gadgetgalaxy.common.viewBinding
import com.example.gadgetgalaxy.common.visible
import com.example.gadgetgalaxy.ui.login.AuthState
import com.example.gadgetgalaxy.ui.login.AuthViewModel
import com.example.gadgetgalaxy.R
import com.example.gadgetgalaxy.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

        with(binding) {
            btnLogin.setOnClickListener {
                if (checkInfos(etEmail2.text.toString(), etPassword2.text.toString())) {
                    viewModel.signup(etEmail2.text.toString(), etPassword2.text.toString())
                }
            }

            tvGoToSignin.setOnClickListener {
                findNavController().navigate(SignupFragmentDirections.signupToSignin())
            }
        }
    }

    private fun observeData() = with(binding) {

        viewModel.authState.observe(viewLifecycleOwner) { state ->

            when (state) {
                AuthState.Loading -> {
                    binding.progressBar3.visible()
                }

                is AuthState.Data -> {
                    binding.progressBar3.invisible()
                    findNavController().navigate(SignupFragmentDirections.signupToHome())
                }

                is AuthState.Error -> {
                    binding.progressBar3.invisible()
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun checkInfos(email: String, password: String): Boolean {

        val checkInfos = when {
            email.isValidEmail(email).not() -> false
            password.isValidPassword(password).not() -> false
            else -> true
        }
        return checkInfos
    }
}