package com.example.ecommerceapp.ui.tabs.profile.addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.addresses.Address
import com.example.ecommerceapp.databinding.FragmentAddAddressBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAddressFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddAddressBinding
    private lateinit var viewModel: AddressesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddressesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { renderViewStates(it) }
            }
        }
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
    }

    private fun handleEvents(event: AddressesContract.Event?) {
    }

    private fun renderViewStates(state: AddressesContract.State) {
        when (state) {
            is AddressesContract.State.ErrorAdd -> showError(state.message)
            is AddressesContract.State.Initial -> return
            is AddressesContract.State.LoadingAdd -> showLoading()
            is AddressesContract.State.SuccessAdd -> addAddress(state.message, state.addresses)
            else -> {}
        }
    }

    private fun addAddress(message: String, addresses: List<Address?>?) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        onAddressAddedListener?.onAddressAdded(addresses)
        dismiss()

    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun initViews() {
        binding.progressBar.isVisible = false
        binding.btnAddAddress.setOnClickListener {
            viewModel.invokeAction(AddressesContract.Action.AddAddress())
        }
    }

    var onAddressAddedListener: OnAddressAddedListener? = null

    fun interface OnAddressAddedListener {
        fun onAddressAdded(addresses: List<Address?>?)
    }
}