package com.example.ecommerceapp.ui.tabs.insideTabs.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.domain.common.Constants.Companion.BRAND_OBJECT
import com.example.domain.common.Constants.Companion.CATEGORY_OBJECT
import com.example.domain.common.Constants.Companion.PRODUCT_OBJECT
import com.example.domain.common.Constants.Companion.SUB_CATEGORY_OBJECT
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.ecommerceapp.databinding.FragmentProductsBinding
import com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails.ProductDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    var subCategory: SubCategory? = null
    var category: Category? = null
    var brand: Brand? = null
    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel
    private val productsAdapter = ProductsAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        subCategory = arguments?.getParcelable(SUB_CATEGORY_OBJECT)
        category = arguments?.getParcelable(CATEGORY_OBJECT)
        brand = arguments?.getParcelable(BRAND_OBJECT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        Log.d("brand", "onViewCreated: $brand")
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect {
                    renderViewState(it)
                }
            }
        }
        //if subCategory has data, invoke action to load products by subCategory
        if (subCategory != null) {
            subCategory?.let {
                viewModel.invokeAction(ProductsContract.Action.LoadProductsBySubCategory(it))
            }
        } else if (category != null) {
            //if category has data, invoke action to load products by category
            category?.let {
                viewModel.invokeAction(ProductsContract.Action.LoadProductsByCategory(it))
            }
        } else {
            //if brand has data, invoke action to load products by brands
            brand?.let {
                viewModel.invokeAction(ProductsContract.Action.LoadProductsByBrand(it))
            }
        }
    }

    private fun renderViewState(state: ProductsContract.State) {
        when (state) {
            is ProductsContract.State.Error -> showError(state.message, state.subCategory)
            is ProductsContract.State.Loading -> showLoading(state.message)
            is ProductsContract.State.Success -> bindProducts(state.products)
            is ProductsContract.State.ErrorByCategory -> showErrorByCategory(
                state.message,
                state.category
            )

            is ProductsContract.State.ErrorByBrand -> showErrorByBrand(state.message, state.brand)
            is ProductsContract.State.ErrorAdd -> showErrorAdd(state.message)
            is ProductsContract.State.LoadingAdd -> showLoadingAdd()
            is ProductsContract.State.SuccessAdd -> handleSuccessAdd(state.message)
        }
    }

    private fun handleSuccessAdd(message: String) {
        binding.addToWishlistLoadingView.isVisible = false
        binding.successView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingAdd() {
        Log.d("GTAG", "showLoadingAddToWishlist")
        binding.successView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.addToWishlistLoadingView.isVisible = true
        binding.successView.isEnabled = false
    }

    private fun showErrorAdd(message: String) {
        binding.addToWishlistLoadingView.isVisible = false
        binding.successView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorByBrand(message: String, brand: Brand) {
        binding.successView.isVisible = false
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.addToWishlistLoadingView.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(ProductsContract.Action.LoadProductsByBrand(brand))
        }
    }

    private fun showErrorByCategory(message: String, category: Category) {
        binding.successView.isVisible = false
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.addToWishlistLoadingView.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(ProductsContract.Action.LoadProductsByCategory(category))
        }
    }

    private fun showLoading(message: String) {
        binding.successView.isVisible = false
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.addToWishlistLoadingView.isVisible = false
        binding.errorText.text = message
    }

    private fun showError(message: String, subCategory: SubCategory) {
        binding.successView.isVisible = false
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.addToWishlistLoadingView.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(ProductsContract.Action.LoadProductsBySubCategory(subCategory))
        }
    }

    private fun bindProducts(products: List<Product?>) {
        binding.successView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.addToWishlistLoadingView.isVisible = false
        productsAdapter.bindProducts(products)
    }

    private fun handleEvents(event: ProductsContract.Event) {
        when (event) {
            is ProductsContract.Event.NavigateToProductDetails -> navigateToProductDetails(event.product)
        }
    }

    private fun navigateToProductDetails(product: Product) {
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_OBJECT, product)
        startActivity(intent)
    }

    private fun initViews() {
        productsAdapter.onItemClickListener =
            ProductsAdapter.OnItemClickListener { position, product ->
                product?.let {
                    viewModel.invokeAction(ProductsContract.Action.ProductClicked(it))
                }
            }
        binding.productsRecycler.adapter = productsAdapter

        productsAdapter.onAddToWishlistClickListener =
            ProductsAdapter.OnAddToWishlistClickListener { position, product ->
                product?.let {
                    viewModel.invokeAction(ProductsContract.Action.AddToWishlistClicked(it))
                }
            }

        productsAdapter.onAddToCartClickListener =
            ProductsAdapter.OnAddToCartClickListener { position, product ->
                product?.let {
                    viewModel.invokeAction(ProductsContract.Action.AddToCartClicked(it))
                }
            }
    }
}