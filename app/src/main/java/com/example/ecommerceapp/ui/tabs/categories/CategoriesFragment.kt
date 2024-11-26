package com.example.ecommerceapp.ui.tabs.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.domain.common.Constants.Companion.SUB_CATEGORY_OBJECT
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCategoriesBinding
import com.example.ecommerceapp.ui.tabs.insideTabs.products.ProductsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var binding: FragmentCategoriesBinding
    private val categoriesAdapter = CategoriesAdapter(null)
    private val subCategoriesAdapter = SubCategoriesAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
//        viewModel.states.observe(viewLifecycleOwner, ::renderViewState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { renderViewState(it) }
            }
        }
        viewModel.invokeAction(CategoriesContract.Action.LoadCategories)
    }

    private fun initViews() {
        categoriesAdapter.onItemClickListener =
            CategoriesAdapter.OnItemClickListener { position, item ->
                item?.let {
                    viewModel.invokeAction(CategoriesContract.Action.CategoryClicked(it))
                    Log.d("GTAG", "categoryClicked: $it")
                    Glide.with(this)
                        .load(it.image)
                        .error(R.drawable.women_fashion_img)
                        .placeholder(R.drawable.place_holder_img)
                        .into(binding.categoryImage)
                    binding.categoryName.text = it.name
                }
            }
        subCategoriesAdapter.onItemClickListener =
            SubCategoriesAdapter.OnItemClickListener { position, subCategory ->
                subCategory?.let {
                    viewModel.invokeAction(CategoriesContract.Action.SubCategoryClicked(it))
                }
            }
        binding.categoriesRecycler.adapter = categoriesAdapter
        binding.subCategoryRecyclerView.adapter = subCategoriesAdapter
    }

    private fun renderViewState(state: CategoriesContract.State) {
        when (state) {
            is CategoriesContract.State.LoadingByCategory -> showLoading()
            is CategoriesContract.State.ErrorByCategory -> showError(state.message)
            is CategoriesContract.State.SuccessByCategory -> bindCategories(state.categories)
            is CategoriesContract.State.SuccessBySubCategory -> bindSubCategories(state.subCategories)
            is CategoriesContract.State.ErrorBySubCategory -> showErrorBySubCategory(
                state.message,
                state.category
            )
            is CategoriesContract.State.LoadingBySubCategory -> showLoadingBySubCategory(state.message)
        }
    }

    private fun showLoadingBySubCategory(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        binding.errorViewBySubCategory.isVisible = false
        binding.successViewBySubCategory.isVisible = false
        binding.loadingViewBySubCategory.isVisible = true
        binding.loadingTextBySubCategory.text = message
    }

    private fun showErrorBySubCategory(message: String, category: Category) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        binding.errorViewBySubCategory.isVisible = true
        binding.successViewBySubCategory.isVisible = false
        binding.loadingViewBySubCategory.isVisible = false
        binding.errorTextBySubCategory.text = message
        binding.tryAgainBtnBySubCategory.setOnClickListener {
            viewModel.invokeAction(CategoriesContract.Action.CategoryClicked(category))
        }
    }

    private fun bindSubCategories(subCategories: List<SubCategory?>) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        binding.errorViewBySubCategory.isVisible = false
        binding.successViewBySubCategory.isVisible = true
        binding.loadingViewBySubCategory.isVisible = false
        subCategoriesAdapter.bindSubCategories(subCategories)
    }

    private fun showError(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.errorViewBySubCategory.isVisible = false
        binding.successViewBySubCategory.isVisible = false
        binding.loadingViewBySubCategory.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(CategoriesContract.Action.LoadCategories)
        }
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.errorViewBySubCategory.isVisible = false
        binding.successViewBySubCategory.isVisible = false
        binding.loadingViewBySubCategory.isVisible = false

    }

    private fun bindCategories(categories: List<Category?>) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        binding.errorViewBySubCategory.isVisible = false
        binding.successViewBySubCategory.isVisible = true
        binding.loadingViewBySubCategory.isVisible = false
        categoriesAdapter.bindCategories(categories)
        bindFirstItem(categories)
    }

    private fun bindFirstItem(categories: List<Category?>) {
        val firstItemInCategory = categories[0]
        binding.categoryName.text = firstItemInCategory?.name
        Glide.with(this)
            .load(firstItemInCategory?.image)
            .placeholder(R.drawable.place_holder_img)
            .into(binding.categoryImage)
        binding.categoryName.text = firstItemInCategory?.name
    }

    private fun handleEvents(event: CategoriesContract.Event) {
        when (event) {
            is CategoriesContract.Event.NavigateToSubCategories -> navigateToCategory()
            is CategoriesContract.Event.NavigateToProducts -> navigateToProducts(event.subCategory)
        }
    }

    private fun navigateToProducts(subCategory: SubCategory) {
        val bundle = Bundle()
        bundle.putParcelable(SUB_CATEGORY_OBJECT, subCategory)
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


    private fun navigateToCategory() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }


}