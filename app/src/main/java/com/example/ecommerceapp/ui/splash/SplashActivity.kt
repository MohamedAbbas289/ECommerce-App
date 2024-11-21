package com.example.ecommerceapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.ui.tabs.HomeScreen
import com.example.ecommerceapp.ui.tabs.auth.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper())
            .postDelayed({
                navigateToHome()
            }, 1000)
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