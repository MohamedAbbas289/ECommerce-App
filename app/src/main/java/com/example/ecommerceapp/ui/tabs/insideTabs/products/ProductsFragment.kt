package com.example.ecommerceapp.ui.tabs.insideTabs.products

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.ecommerceapp.databinding.FragmentProductsBinding
import com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails.ProductDetailsActivity
import com.example.ecommerceapp.utils.Constants.Companion.CATEGORY_OBJECT
import com.example.ecommerceapp.utils.Constants.Companion.PRODUCT_OBJECT
import com.example.ecommerceapp.utils.Constants.Companion.SUB_CATEGORY_OBJECT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    var subCategory: SubCategory? = null
    var category: Category? = null
    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel
    private val productsAdapter = ProductsAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        subCategory = arguments?.getParcelable(SUB_CATEGORY_OBJECT)
        category = arguments?.getParcelable(CATEGORY_OBJECT)
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
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.states.observe(viewLifecycleOwner, ::renderViewState)
        //if subCategory has data, invoke action to load products by subCategory
        if (subCategory != null) {
            subCategory?.let {
                viewModel.invokeAction(ProductsContract.Action.LoadProductsBySubCategory(it))
            }
        } else {
            //if subCategory is null, invoke action to load products by category
            category?.let {
                viewModel.invokeAction(ProductsContract.Action.LoadProductsByCategory(it))
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
        }
    }

    private fun showErrorByCategory(message: String, category: Category) {
        binding.successView.isVisible = false
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(ProductsContract.Action.LoadProductsByCategory(category))
        }
    }

    private fun showLoading(message: String) {
        binding.successView.isVisible = false
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.errorText.text = message
    }

    private fun showError(message: String, subCategory: SubCategory) {
        binding.successView.isVisible = false
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(ProductsContract.Action.LoadProductsBySubCategory(subCategory))
        }
    }

    private fun bindProducts(products: List<Product?>) {
        binding.successView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
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
    }

}