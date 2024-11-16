package com.example.ecommerceapp.ui.tabs.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityRegisterBinding
import com.example.ecommerceapp.ui.tabs.HomeScreen

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.signUpBtn.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }
}