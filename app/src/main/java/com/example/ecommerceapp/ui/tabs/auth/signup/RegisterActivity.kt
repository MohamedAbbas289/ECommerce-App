package com.example.ecommerceapp.ui.tabs.auth.signup

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
import com.example.domain.model.user.UserResponse
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityRegisterBinding
import com.example.ecommerceapp.ui.tabs.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
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
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.NavigateToHome -> navigateToHome()
        }
    }

    private fun renderViewStates(state: RegisterContract.State) {
        when (state) {
            is RegisterContract.State.Error -> showError(state.message)
            is RegisterContract.State.Loading -> showLoading()
            is RegisterContract.State.Success -> handleSuccess(state.userResponse)
            is RegisterContract.State.Nothing -> return
        }
    }

    private fun handleSuccess(userResponse: UserResponse) {
        binding.progressBar.isVisible = false
        Toast.makeText(this, "Welcome ${userResponse.user?.name}", Toast.LENGTH_LONG).show()
    }


    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.signUpBtn.setOnClickListener {
            viewModel.invokeAction(RegisterContract.Action.Register())
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }
}