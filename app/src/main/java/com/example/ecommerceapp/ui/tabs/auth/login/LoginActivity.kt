package com.example.ecommerceapp.ui.tabs.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityLoginBinding
import com.example.ecommerceapp.ui.tabs.HomeScreen
import com.example.ecommerceapp.ui.tabs.auth.signup.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { renderViewStates(it) }
            }
        }
        //viewModel.states.observe(this, ::renderViewStates)
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.NavigateToHome -> navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun renderViewStates(state: LoginContract.State) {
        when (state) {
            is LoginContract.State.Error -> showError(state.message)
            is LoginContract.State.Loading -> showLoading()
            is LoginContract.State.Success -> loginSuccess()
            is LoginContract.State.Nothing -> return
        }
    }

    private fun loginSuccess() {
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.lottieAnimationView.isVisible = true
    }

    private fun showError(message: String) {
        binding.lottieAnimationView.isVisible = false
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.loginBtn.setOnClickListener {
            viewModel.invokeAction(LoginContract.Action.Login())
        }
        binding.createAccountBtn.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}