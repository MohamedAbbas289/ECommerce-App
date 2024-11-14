package com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codebyashish.autoimageslider.Enums.ImageScaleType
import com.codebyashish.autoimageslider.Models.ImageSlidesModel
import com.example.domain.model.Product
import com.example.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.example.ecommerceapp.utils.Constants.Companion.PRODUCT_OBJECT
import setResizableText

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    val viewModel: ProductDetailsViewModel by viewModels()
    private var productCounter = 1
    var product: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        product = intent.getParcelableExtra(PRODUCT_OBJECT)
        binding.product = product
        binding.productDescription
            .setResizableText(
                product?.description!! + " ", 3, true
            )
        binding.backIc.setOnClickListener {
            finish()
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