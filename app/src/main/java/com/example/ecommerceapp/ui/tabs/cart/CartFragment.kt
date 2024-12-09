package com.example.ecommerceapp.ui.tabs.cart

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
import com.example.domain.model.cart.Cart
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCartBinding
import com.example.ecommerceapp.utils.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel
    private val cartAdapter = CartAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
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
            viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        }
        viewModel.invokeAction(CartContract.Action.LoadCart())
    }

    private fun handleEvents(event: CartContract.Event) {
        when (event) {
            is CartContract.Event.NavigateToCheckout -> TODO()
        }
    }

    private fun renderViewStates(state: CartContract.State) {
        when (state) {
            is CartContract.State.Error -> showError(state.message)
            is CartContract.State.Loading -> showLoading()
            is CartContract.State.Success -> handleSuccess(state.cart)
            is CartContract.State.ErrorRemovingProduct -> handleRemovingProductError(state.message)
            is CartContract.State.SuccessRemovingProduct -> removeProduct(state.message, state.cart)
            is CartContract.State.LoadingRemovingProduct -> handleRemovingProductLoading()
            is CartContract.State.ClearCartSuccess -> clearCartSuccess(state.message)
        }

    }

    private fun clearCartSuccess(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        cartAdapter.clearProducts()
        checkIfCartIsEmpty()
    }

    private fun handleRemovingProductLoading() {
        binding.progressBar.isVisible = true
    }

    private fun removeProduct(message: String, cart: Cart) {
        binding.progressBar.isVisible = false
        binding.totalPriceTv.text = "EGP " + cart.totalCartPrice
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun handleRemovingProductError(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.lottieAnimationView.isVisible = true
        binding.successView.isVisible = false
        binding.errorView.isVisible = false
    }

    private fun showError(message: String) {
        binding.lottieAnimationView.isVisible = false
        binding.successView.isVisible = false
        binding.errorView.isVisible = true
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(CartContract.Action.LoadCart())
        }
    }

    private fun handleSuccess(cart: Cart) {
        binding.lottieAnimationView.isVisible = false
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.totalPriceTv.text = "EGP " + cart.totalCartPrice
        cartAdapter.bindProducts(cart.products?.toMutableList())
        checkIfCartIsEmpty()
    }

    private fun checkIfCartIsEmpty(): Boolean {
        val isEmpty = cartAdapter.itemCount == 0
        if (isEmpty) {
            binding.lottieAnimationView.isVisible = false
            binding.successView.isVisible = false
            binding.errorView.isVisible = true
            binding.tryAgainBtn.isVisible = false
            binding.errorText.text = getString(R.string.your_cart_is_empty)
        } else {
            binding.successView.isVisible = true
            binding.errorView.isVisible = false
        }
        return isEmpty
    }


    private fun initViews() {
        binding.progressBar.isVisible = false
        cartAdapter.onRemoveFromCartClickListener =
            CartAdapter.OnRemoveFromCartClickListener { productsItem, position ->
                viewModel.invokeAction(CartContract.Action.RemoveProductFromCart(productsItem.product?.id!!))
                cartAdapter.removeItem(position)
                checkIfCartIsEmpty()

            }

        binding.deleteAllIc.setOnClickListener {
            showMessage(
                message = "are you sure you want to delete all products from your cart?",
                posActionName = "yes",
                negActionName = "cancel",
                posAction = { dialog, _ ->
                    viewModel.invokeAction(CartContract.Action.ClearCart())
                    dialog.dismiss()
                }
            )
        }
        binding.cartRecycler.adapter = cartAdapter
    }
}