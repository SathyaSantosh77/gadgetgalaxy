package com.example.gadgetgalaxy.ui.login.signin

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
import com.example.gadgetgalaxy.databinding.FragmentSigninBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment(R.layout.fragment_signin) {

    private val binding by viewBinding(FragmentSigninBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

        with(binding) {
            button.setOnClickListener {
                if (checkInfos(etEmail.text.toString(), etPassword.text.toString())) {
                    viewModel.signin(etEmail.text.toString(), etPassword.text.toString())
                }
            }
            tvGoToSignup.setOnClickListener {
                findNavController().navigate(SigninFragmentDirections.signinToSignup())
            }
        }
    }

    private fun observeData() = with(binding) {

        viewModel.authState.observe(viewLifecycleOwner) { state ->

            when (state) {
                AuthState.Loading -> {
                    binding.progressBar.visible()
                }

                is AuthState.Data -> {
                    binding.progressBar.invisible()
                    findNavController().navigate(SigninFragmentDirections.signinToHome())
                }

                is AuthState.Error -> {
                    binding.progressBar.invisible()
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