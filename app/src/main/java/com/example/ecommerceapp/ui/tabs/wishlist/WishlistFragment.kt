package com.example.ecommerceapp.ui.tabs.wishlist

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
import com.example.domain.model.Product
import com.example.ecommerceapp.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private lateinit var binding: FragmentWishlistBinding
    private lateinit var viewModel: WishlistViewModel
    private val wishlistAdapter = WishlistProductsAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WishlistViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
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
                    renderViewState(it)
                }
            }
        }
        viewModel.invokeAction(WishlistContract.Action.LoadWishlist())
    }

    private fun renderViewState(state: WishlistContract.State) {
        when (state) {
            is WishlistContract.State.Error -> handleError(state.message)
            is WishlistContract.State.Loading -> showLoading()
            is WishlistContract.State.Success -> bindWishlistProducts(state.wishlistProducts)
            is WishlistContract.State.RemoveFromWishlistError -> handleRemoveFromWishlistError(state.message)
            is WishlistContract.State.RemoveFromWishlistLoading -> handleRemoveFromWishlistLoading()
            is WishlistContract.State.RemoveFromWishlistSuccess -> removeProductFromWishlist()
            is WishlistContract.State.AddToCartSuccess -> addProductToCart(state.message)
        }
    }

    private fun addProductToCart(message: String) {
        binding.progressBar.isVisible = false
        binding.successView.isEnabled = true
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun removeProductFromWishlist() {
        binding.progressBar.isVisible = false
        binding.successView.isEnabled = true
        Toast.makeText(requireContext(), "Product Removed", Toast.LENGTH_SHORT).show()
    }

    private fun handleRemoveFromWishlistLoading() {
        binding.progressBar.isVisible = true
        binding.successView.isEnabled = false
    }

    private fun handleRemoveFromWishlistError(message: String) {
        binding.progressBar.isVisible = false
        binding.successView.isEnabled = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun handleError(message: String) {
        binding.errorView.isVisible = true
        binding.lottieAnimationView.isVisible = false
        binding.successView.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(WishlistContract.Action.LoadWishlist())
        }
    }

    private fun showLoading() {
        binding.errorView.isVisible = false
        binding.lottieAnimationView.isVisible = true
        binding.successView.isVisible = false
    }

    private fun bindWishlistProducts(wishlistProducts: List<Product?>?) {
        binding.errorView.isVisible = false
        binding.lottieAnimationView.isVisible = false
        binding.successView.isVisible = true
        wishlistAdapter.bindWishlistProducts(wishlistProducts?.toMutableList())
    }

    private fun initViews() {
        binding.progressBar.isVisible = false
        wishlistAdapter.onRemoveFromWishlistClickListener =
            WishlistProductsAdapter.OnRemoveFromWishlistClickListener { product, position ->
                viewModel.invokeAction(WishlistContract.Action.RemoveFromWishlist(product.id!!))
                wishlistAdapter.removeItem(position)
            }
        wishlistAdapter.onAddToCartClickListener =
            WishlistProductsAdapter.OnAddToCartClickListener { product, _ ->
                viewModel.invokeAction(WishlistContract.Action.AddToCart(product.id!!))
            }
        binding.wishlistProductsRecycler.adapter = wishlistAdapter
    }
}