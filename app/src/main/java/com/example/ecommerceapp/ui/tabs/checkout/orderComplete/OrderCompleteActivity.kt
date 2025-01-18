package com.example.ecommerceapp.ui.tabs.checkout.orderComplete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.common.Constants.Companion.ORDER_DETAILS_KEY
import com.example.domain.model.order.OrderDetails
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityOrderCompleteBinding
import com.example.ecommerceapp.ui.tabs.HomeScreen

class OrderCompleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderDetails = intent.getParcelableExtra<OrderDetails>(ORDER_DETAILS_KEY)

        initViews(orderDetails)
    }

    private fun initViews(orderDetails: OrderDetails?) {
        binding.totalAmountValue.text = orderDetails?.totalOrderPrice.toString()
        binding.deliveryChargeValue.text = orderDetails?.shippingPrice.toString()
        binding.paymentMethodValue.text = orderDetails?.paymentMethodType.toString()

        binding.continueShoppingBtn.setOnClickListener {
            navigateToHome()
        }
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        navigateToHome()
        //finishAffinity()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }
}