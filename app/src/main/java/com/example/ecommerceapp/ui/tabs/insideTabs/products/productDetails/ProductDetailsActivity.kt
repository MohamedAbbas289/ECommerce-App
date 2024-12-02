package com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codebyashish.autoimageslider.Enums.ImageScaleType
import com.codebyashish.autoimageslider.Models.ImageSlidesModel
import com.example.domain.common.Constants.Companion.PRODUCT_OBJECT
import com.example.domain.model.Product
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import setResizableText

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: ProductDetailsViewModel by viewModels()
    private var productCounter = 1
    var product: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
    }


    private fun renderViewState(state: ProductDetailsContract.State) {
        when (state) {
            is ProductDetailsContract.State.AddToWishlistSuccess -> handleSuccessAddToWishlist(state.message)
            is ProductDetailsContract.State.Error -> handleError(state.message)
            is ProductDetailsContract.State.Initial -> return
            is ProductDetailsContract.State.Loading -> handleLoading()
        }
    }

    private fun handleLoading() {
        binding.progressBar.isVisible = true
    }

    private fun handleError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        binding.progressBar.isVisible = false
    }

    private fun handleSuccessAddToWishlist(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        binding.progressBar.isVisible = false
        binding.addWishList.setImageResource(R.drawable.ic_added_to_wishlist)
        binding.addWishList.isEnabled = false
    }

    private fun initViews() {
        binding.progressBar.isVisible = false
        product = intent.getParcelableExtra(PRODUCT_OBJECT)
        binding.product = product
        binding.productDescription
            .setResizableText(
                product?.description!! + " ", 3, true
            )
        binding.backIc.setOnClickListener {
            finish()
        }
        binding.addWishList.setOnClickListener {
            viewModel.invokeAction(ProductDetailsContract.Action.AddToWishlistClicked(product!!))
        }
        bindProductImages()
        productCounterCalc()
    }

    private fun productCounterCalc() {
        binding.increaseCount.setOnClickListener {
            if (productCounter < (product?.quantity ?: 0)) {
                productCounter++
            }
            binding.counterTxt.text = productCounter.toString()
        }
        binding.decreaseCount.setOnClickListener {
            if (productCounter > 1) {
                productCounter--
            }
            binding.counterTxt.text = productCounter.toString()
        }
    }

    private fun bindProductImages() {
        val productImagesList = ArrayList<ImageSlidesModel>()
        val autoImageSlider = binding.autoImageSlider

        product?.images?.forEach {
            productImagesList.add(ImageSlidesModel(it))
        }
        autoImageSlider.setImageList(productImagesList, ImageScaleType.FIT)
        autoImageSlider.setDefaultAnimation()
    }


}