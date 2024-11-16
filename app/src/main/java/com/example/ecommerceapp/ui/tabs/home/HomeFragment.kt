package com.example.ecommerceapp.ui.tabs.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codebyashish.autoimageslider.Enums.ImageScaleType
import com.codebyashish.autoimageslider.Models.ImageSlidesModel
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentHomeBinding
import com.example.ecommerceapp.ui.tabs.insideTabs.products.ProductsAdapter
import com.example.ecommerceapp.ui.tabs.insideTabs.products.ProductsFragment
import com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails.ProductDetailsActivity
import com.example.ecommerceapp.utils.Constants.Companion.BRAND_OBJECT
import com.example.ecommerceapp.utils.Constants.Companion.CATEGORY_OBJECT
import com.example.ecommerceapp.utils.Constants.Companion.PRODUCT_OBJECT
import com.example.ecommerceapp.utils.Constants.Companion.TV_SUB_CATEGORY_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val categoriesAdapter = CategoriesHomeAdapter(null)
    private val productsAdapter = ProductsAdapter(null)
    private val brandsAdapter = BrandsAdapter(null)
    private val tvSubCategory = SubCategory(id = TV_SUB_CATEGORY_ID)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.states.observe(viewLifecycleOwner, ::renderViewState)
        viewModel.invokeAction(HomeContract.Action.LoadCategories)
        viewModel.invokeAction(HomeContract.Action.LoadProducts(tvSubCategory))
        viewModel.invokeAction(HomeContract.Action.LoadBrands)
    }

    private fun renderViewState(state: HomeContract.State) {
        when (state) {
            is HomeContract.State.Error -> showError(state.message)
            is HomeContract.State.Loading -> showLoading()
            is HomeContract.State.Success -> bindCategories(state.categories)
            is HomeContract.State.SuccessByProducts -> bindProducts(state.products)
            is HomeContract.State.SuccessByBrands -> bindBrands(state.brands)
        }
    }

    private fun bindBrands(brands: List<Brand?>) {
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = false
        brandsAdapter.bindBrands(brands)
    }

    private fun bindProducts(products: List<Product?>) {
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = false
        productsAdapter.bindProducts(products)
    }

    private fun bindCategories(categories: List<Category?>) {
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = false
        categoriesAdapter.bindCategories(categories)
    }

    private fun showLoading() {
        binding.successView.isVisible = false
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = true
    }

    private fun showError(message: String) {
        binding.successView.isVisible = false
        binding.errorView.isVisible = true
        binding.loadingView.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(HomeContract.Action.LoadCategories)
        }
    }

    private fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.NavigateToProductsDetails -> navigateToProductsDetails(event.product)
        }
    }

    private fun navigateToProductsDetails(product: Product) {
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_OBJECT, product)
        startActivity(intent)
    }

    private fun initViews() {
        bindAdsImages()
        productsAdapter.onItemClickListener =
            ProductsAdapter.OnItemClickListener { position, product ->
                product?.let {
                    viewModel.invokeAction(HomeContract.Action.ProductClicked(it))
                }
            }

        categoriesAdapter.onItemClickListener =
            CategoriesHomeAdapter.OnItemClickListener { position, category ->
                navigateToProductsByCategory(category)
            }

        brandsAdapter.onItemClickListener =
            BrandsAdapter.OnItemClickListener { position, brand ->
                navigateToProductsByBrand(brand)
            }
        binding.categoriesRecycler.adapter = categoriesAdapter
        binding.tvRecycler.adapter = productsAdapter
        binding.brandsRecycler.adapter = brandsAdapter
    }

    private fun navigateToProductsByBrand(brand: Brand?) {
        val bundle = Bundle()
        bundle.putParcelable(BRAND_OBJECT, brand)
        val productsFragment = ProductsFragment()
        productsFragment.arguments = bundle
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .hide(this)
            .add(R.id.fragment_container, productsFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToProductsByCategory(category: Category?) {
        val bundle = Bundle()
        bundle.putParcelable(CATEGORY_OBJECT, category)
        val productsFragment = ProductsFragment()
        productsFragment.arguments = bundle
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .hide(this)
            .add(R.id.fragment_container, productsFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun bindAdsImages() {
        val adImagesList = ArrayList<ImageSlidesModel>()
        val autoImageSlider = binding.autoImageSlider

        adImagesList.add(ImageSlidesModel(R.drawable.first_ad))
        adImagesList.add(ImageSlidesModel(R.drawable.second_ad))
        adImagesList.add(ImageSlidesModel(R.drawable.third_ad))

        autoImageSlider.setImageList(adImagesList, ImageScaleType.FIT)
        autoImageSlider.setDefaultAnimation()
    }
}