package com.example.ecommerceapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.ui.tabs.HomeScreen
import com.example.ecommerceapp.ui.tabs.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.checkUserSession()
        observeNavigation()
    }

    private fun observeNavigation() {
        splashViewModel.navigateTo.observe(this) { destination ->
            when (destination) {
                SplashViewModel.Destination.HOME -> navigateToHome()
                SplashViewModel.Destination.LOGIN -> navigateToLogin()
            }
        }

    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
