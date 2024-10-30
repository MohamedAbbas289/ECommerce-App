package com.example.ecommerceapp.ui.home.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Category
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]

    }

    private val categoriesAdapter = CategoriesAdapter(null)
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
                }
            }
        binding.categoriesRecycler.adapter = categoriesAdapter
    }

    private fun renderViewState(state: CategoriesContract.State) {
        when (state) {
            is CategoriesContract.State.Loading -> showLoading(state.message)
            is CategoriesContract.State.Error -> showError(state.message)
            is CategoriesContract.State.Success -> bindCategories(state.categories)
        }
    }

    private fun showError(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.errorText.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(CategoriesContract.Action.LoadCategories)
        }
    }

    private fun showLoading(message: String) {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.loadingText.text = message
    }

    private fun bindCategories(categories: List<Category?>) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        categoriesAdapter.bindCategories(categories)
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