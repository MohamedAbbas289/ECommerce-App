package com.example.ecommerceapp.ui.tabs.checkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.common.Constants.Companion.CART_ID_KEY
import com.example.domain.common.Constants.Companion.ORDER_DETAILS_KEY
import com.example.domain.model.OrderRequest
import com.example.domain.model.addresses.Address
import com.example.domain.model.order.OrderDetails
import com.example.domain.model.order.ShippingAddress
import com.example.ecommerceapp.databinding.FragmentCheckoutBinding
import com.example.ecommerceapp.ui.tabs.checkout.orderComplete.OrderCompleteActivity
import com.example.ecommerceapp.ui.tabs.profile.addresses.AddAddressFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel
    private var locationsAdapter = LocationsAdapter(null)
    private var cartId: String? = null
    private var selectedAddress: Address? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
        cartId = arguments?.getString(CART_ID_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
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
                viewModel.state.collect {
                    renderViewState(it)
                }
            }
        }
        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.invokeAction(CheckoutContract.Action.LoadUserAddresses())
    }

    private fun handleEvents(event: CheckoutContract.Event) {
        when (event) {
            is CheckoutContract.Event.NavigateToOrderCompleteScreen -> navigateToOrderCompleteScreen(
                event.orderDetails
            )

        }
    }

    private fun navigateToOrderCompleteScreen(orderDetails: OrderDetails) {
        val intent = Intent(requireContext(), OrderCompleteActivity::class.java)
        intent.putExtra(ORDER_DETAILS_KEY, orderDetails)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun renderViewState(state: CheckoutContract.State) {
        when (state) {
            is CheckoutContract.State.Error -> handleError(state.message)
            is CheckoutContract.State.Initial -> return
            is CheckoutContract.State.Loading -> showLoading()
            is CheckoutContract.State.Success -> bindAddresses(state.addresses)
            is CheckoutContract.State.ErrorOrder -> showOrderError(state.message)
            is CheckoutContract.State.LoadingOrder -> showOrderLoading()
            is CheckoutContract.State.SuccessOrder -> handleOrderSuccess()
            is CheckoutContract.State.SuccessOnlineOrder -> navigateToPaymentScreen(state.session.url!!)
        }
    }

    private fun navigateToPaymentScreen(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        view?.context?.startActivity(intent)
        requireActivity().finish()
    }

    private fun handleOrderSuccess() {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), "Order placed successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showOrderLoading() {
        binding.progressBar.isVisible = true
    }

    private fun showOrderError(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun bindAddresses(addresses: List<Address?>) {
        binding.lottieAnimationView.isVisible = false
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        locationsAdapter.bindAddresses(addresses)

        // setting the default selected address to the first one, if available
        if (addresses.isNotEmpty()) {
            selectedAddress = addresses.first()
        }
    }


    private fun showLoading() {
        binding.lottieAnimationView.isVisible = true
        binding.successView.isVisible = false
        binding.errorView.isVisible = false
    }

    private fun handleError(message: String) {
        binding.lottieAnimationView.isVisible = false
        binding.successView.isVisible = false
        binding.errorView.isVisible = true
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(CheckoutContract.Action.LoadUserAddresses())
        }
    }

    private fun isAddressSelected(): Boolean {
        return selectedAddress != null
    }

    private fun initViews() {
        binding.progressBar.isVisible = false
        locationsAdapter.onAddressSelectedListener =
            LocationsAdapter.OnAddressSelectedListener {
                selectedAddress = it
                Log.d("selectedAddress: ", selectedAddress.toString())
            }

        binding.addNewAddressBtn.setOnClickListener {
            val addAddressFragment = AddAddressFragment()
            addAddressFragment.onAddressAddedListener =
                AddAddressFragment.OnAddressAddedListener { addresses ->
                    selectedAddress = addresses?.first()
                    locationsAdapter.bindAddresses(addresses)
                }
            addAddressFragment.show(childFragmentManager, "AddAddressFragment")

        }
        binding.confirmOrderBtn.setOnClickListener {
            if (!isAddressSelected()) {
                Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val shippingAddress = ShippingAddress(
                city = selectedAddress?.city,
                details = selectedAddress?.details,
                phone = selectedAddress?.phone,
            )
            val orderRequest = OrderRequest(
                shippingAddress = shippingAddress
            )
            if (binding.radioGroup.checkedRadioButtonId == binding.cashOnDeliveryBtn.id) {
                viewModel.invokeAction(
                    CheckoutContract.Action.CreateCashOrder(
                        cartId!!,
                        orderRequest
                    )
                )
            } else {
                viewModel.invokeAction(
                    CheckoutContract.Action.CreateCreditCardOrder(
                        cartId!!,
                        orderRequest
                    )
                )
            }

        }
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.addressesRecycler.adapter = locationsAdapter
    }
}