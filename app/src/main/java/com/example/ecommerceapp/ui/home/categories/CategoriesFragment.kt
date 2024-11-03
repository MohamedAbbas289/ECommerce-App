package com.example.ecommerceapp.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.states.observe(viewLifecycleOwner, ::renderViewState)
        viewModel.invokeAction(CategoriesContract.Action.LoadCategories)
    }

    private fun initViews() {
        categoriesAdapter.onItemClickListener =
            CategoriesAdapter.OnItemClickListener { position, item ->
                item?.let {
                    viewModel.invokeAction(CategoriesContract.Action.CategoryClicked(it))
                    Glide.with(this)
                        .load(it.image)
                        .placeholder(R.drawable.place_holder_img)
                        .into(binding.categoryImage)
                    binding.categoryName.text = it.name
                }
            }
        binding.categoriesRecycler.adapter = categoriesAdapter
        binding.subCategoryRecyclerView.adapter = subCategoriesAdapter
    }

    private fun renderViewState(state: CategoriesContract.State) {
        when (state) {
            is CategoriesContract.State.LoadingByCategory -> showLoading(state.message)
            is CategoriesContract.State.ErrorByCategory -> showError(state.message)
            is CategoriesContract.State.SuccessByCategory -> bindCategories(state.categories)
            is CategoriesContract.State.SuccessBySubCategory -> bindSubCategories(state.subCategories)
            is CategoriesContract.State.ErrorBySubCategory -> showErrorBySubCategory(state.message)
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
    }

    private fun showErrorBySubCategory(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        binding.errorViewBySubCategory.isVisible = true
        binding.successViewBySubCategory.isVisible = false
        binding.loadingViewBySubCategory.isVisible = false
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

    private fun showLoading(message: String) {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.errorViewBySubCategory.isVisible = false
        binding.successViewBySubCategory.isVisible = false
        binding.loadingViewBySubCategory.isVisible = false
        binding.loadingText.text = message
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
        }
    }

    private fun navigateToCategory() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }


}