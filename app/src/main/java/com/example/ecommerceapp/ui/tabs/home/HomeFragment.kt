package com.example.ecommerceapp.ui.tabs.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codebyashish.autoimageslider.Enums.ImageScaleType
import com.codebyashish.autoimageslider.Models.ImageSlidesModel
import com.example.domain.common.Constants.Companion.BRAND_OBJECT
import com.example.domain.common.Constants.Companion.CATEGORY_OBJECT
import com.example.domain.common.Constants.Companion.PRODUCT_OBJECT
import com.example.domain.common.Constants.Companion.TV_SUB_CATEGORY_ID
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentHomeUpdatedBinding
import com.example.ecommerceapp.ui.tabs.categories.CategoriesFragment
import com.example.ecommerceapp.ui.tabs.insideTabs.products.ProductsAdapter
import com.example.ecommerceapp.ui.tabs.insideTabs.products.ProductsFragment
import com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails.ProductDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeUpdatedBinding
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
        binding = FragmentHomeUpdatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect {
                    renderViewState(it)
                }
            }
        }

        viewModel.invokeAction(HomeContract.Action.LoadCategories)
        viewModel.invokeAction(HomeContract.Action.LoadProducts(tvSubCategory))
        viewModel.invokeAction(HomeContract.Action.LoadBrands)
    }

    private fun renderViewState(state: HomeContract.State) {
        when (state) {
            is HomeContract.State.ErrorByCategories -> showCategoriesError(state.message)
            is HomeContract.State.LoadingByCategories -> showCategoriesLoading()
            is HomeContract.State.Success -> bindCategories(state.categories)
            is HomeContract.State.SuccessByProducts -> bindProducts(state.products)
            is HomeContract.State.SuccessByBrands -> bindBrands(state.brands)
            is HomeContract.State.Initial -> return
            is HomeContract.State.ErrorByBrands -> showBrandsError(state.message)
            is HomeContract.State.ErrorByProducts -> showProductsError(state.message)
            is HomeContract.State.LoadingByBrands -> showBrandsLoading()
            is HomeContract.State.LoadingByProducts -> showProductsLoading()
        }
    }

    private fun showProductsLoading() {
        binding.successViewByTV.isVisible = false
        binding.errorViewByTV.isVisible = false
        binding.loadingViewByTV.isVisible = true
    }

    private fun showBrandsLoading() {
        binding.successViewByBrands.isVisible = false
        binding.errorViewByBrands.isVisible = false
        binding.loadingViewByBrands.isVisible = true
    }

    private fun showProductsError(message: String) {
        binding.successViewByTV.isVisible = false
        binding.errorViewByTV.isVisible = true
        binding.loadingViewByTV.isVisible = false
        binding.errorTextByTV.text = message
        binding.tryAgainBtnByTV.setOnClickListener {
            viewModel.invokeAction(HomeContract.Action.LoadProducts(tvSubCategory))
        }
    }

    private fun showBrandsError(message: String) {
        binding.successViewByBrands.isVisible = false
        binding.errorViewByBrands.isVisible = true
        binding.loadingViewByBrands.isVisible = false
        binding.errorTextByBrands.text = message
        binding.tryAgainBtnByBrands.setOnClickListener {
            viewModel.invokeAction(HomeContract.Action.LoadBrands)
        }
    }

    private fun bindBrands(brands: List<Brand?>) {
        binding.successViewByBrands.isVisible = true
        binding.errorViewByBrands.isVisible = false
        binding.loadingViewByBrands.isVisible = false
        brandsAdapter.bindBrands(brands)
    }

    private fun bindProducts(products: List<Product?>) {
        binding.successViewByTV.isVisible = true
        binding.errorViewByTV.isVisible = false
        binding.loadingViewByTV.isVisible = false
        productsAdapter.bindProducts(products)
    }

    private fun bindCategories(categories: List<Category?>) {
        binding.successViewByCategories.isVisible = true
        binding.errorViewByCategories.isVisible = false
        binding.loadingViewByCategories.isVisible = false
        categoriesAdapter.bindCategories(categories)
    }

    private fun showCategoriesLoading() {
        binding.successViewByCategories.isVisible = false
        binding.errorViewByCategories.isVisible = false
        binding.loadingViewByCategories.isVisible = true
    }

    private fun showCategoriesError(message: String) {
        binding.successViewByCategories.isVisible = false
        binding.errorViewByCategories.isVisible = true
        binding.loadingViewByCategories.isVisible = false
        binding.errorTextByCategories.text = message
        binding.tryAgainBtnByCategories.setOnClickListener {
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
        binding.viewAllTxt.setOnClickListener {
            navigateToCategories()
        }
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

    private fun navigateToCategories() {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .replace(R.id.fragment_container, CategoriesFragment())
            .commit()
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