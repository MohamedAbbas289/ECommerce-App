package com.example.ecommerceapp.ui.tabs

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityHomeScreenBinding
import com.example.ecommerceapp.ui.tabs.cart.CartFragment
import com.example.ecommerceapp.ui.tabs.categories.CategoriesFragment
import com.example.ecommerceapp.ui.tabs.home.HomeFragment
import com.example.ecommerceapp.ui.tabs.profile.ProfileFragment
import com.example.ecommerceapp.ui.tabs.wishlist.WishlistFragment
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : AppCompatActivity(), OnItemSelectedListener {
    lateinit var binding: ActivityHomeScreenBinding
    val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        binding.bottomNavHome.setOnItemSelectedListener(this)
        binding.bottomNavHome.selectedItemId = R.id.navigation_home
        binding.icCart.setOnClickListener {
            binding.topBar.visibility = View.GONE
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
                .add(R.id.fragment_container, CartFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                binding.topBar.visibility = View.VISIBLE
                showFragment(HomeFragment())
            }

            R.id.navigation_categories -> {
                binding.topBar.visibility = View.VISIBLE
                showFragment(CategoriesFragment())
            }

            R.id.navigation_wishlist -> {
                binding.topBar.visibility = View.VISIBLE
                showFragment(WishlistFragment())
            }

            R.id.navigation_profile -> {
                binding.topBar.visibility = View.GONE
                showFragment(ProfileFragment())
            }

        }
        return true
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}