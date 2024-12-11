package com.example.ecommerceapp.ui.tabs.profile.addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.addresses.Address
import com.example.ecommerceapp.databinding.FragmentAddressesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressesFragment : Fragment() {
    private lateinit var binding: FragmentAddressesBinding
    private lateinit var viewModel: AddressesViewModel
    private val adapter = AddressesAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddressesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect {
                    renderViewStates(it)
                }
            }
        }
        viewModel.invokeAction(AddressesContract.Action.LoadAddresses())
    }

    private fun renderViewStates(state: AddressesContract.State) {
        when (state) {
            is AddressesContract.State.Error -> showError(state.message)
            is AddressesContract.State.Initial -> return
            is AddressesContract.State.Loading -> showLoading()
            is AddressesContract.State.Success -> bindAddresses(state.addresses)
            is AddressesContract.State.ErrorRemove -> handleRemoveError(state.message)
            is AddressesContract.State.LoadingRemove -> handleRemoveLoading()
            is AddressesContract.State.SuccessRemove -> removeAddress(state.message)
            else -> {}
        }
    }

    private fun removeAddress(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun handleRemoveLoading() {
        binding.progressBar.isVisible = true
    }

    private fun handleRemoveError(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun bindAddresses(addresses: List<Address?>?) {
        binding.lottieAnimationView.isVisible = false
        binding.errorView.isVisible = false
        binding.addressesRecycler.isVisible = true
        adapter.bindAddresses(addresses?.toMutableList())
    }

    private fun showLoading() {
        binding.lottieAnimationView.isVisible = true
        binding.errorView.isVisible = false
        binding.addressesRecycler.isVisible = false
    }

    private fun showError(message: String) {
        binding.lottieAnimationView.isVisible = false
        binding.errorText.text = message
        binding.errorView.isVisible = true
        binding.addressesRecycler.isVisible = false
    }

    private fun initViews() {
        binding.progressBar.isVisible = false
        binding.addressesRecycler.adapter = adapter
        binding.addAddressFab.setOnClickListener {
            val addAddressFragment = AddAddressFragment()
            addAddressFragment.onAddressAddedListener =
                AddAddressFragment.OnAddressAddedListener { addresses ->
                    adapter.bindAddresses(addresses?.toMutableList())
                }
            addAddressFragment.show(childFragmentManager, "AddAddressFragment")

        }
        adapter.onAddressDeletedListener =
            AddressesAdapter.OnAddressDeletedListener { address, position ->
                viewModel.invokeAction(AddressesContract.Action.RemoveAddress(address.id!!))
                adapter.removeAddress(position)
            }

    }

}