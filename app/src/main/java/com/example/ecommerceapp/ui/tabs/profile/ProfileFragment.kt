package com.example.ecommerceapp.ui.tabs.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentProfileBinding
import com.example.ecommerceapp.ui.tabs.auth.login.LoginActivity
import com.example.ecommerceapp.ui.tabs.profile.addresses.AddressesFragment
import com.example.ecommerceapp.ui.tabs.profile.userInfo.UserInfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun initViews() {
        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
            navigateToLogin()
        }

        binding.myInformationBtn.setOnClickListener {
            navigateToFragment(UserInfoFragment())
        }

        binding.shippingAddressBtn.setOnClickListener {
            navigateToFragment(AddressesFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .hide(this)
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}