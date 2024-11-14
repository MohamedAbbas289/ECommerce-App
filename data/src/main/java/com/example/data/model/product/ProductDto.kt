package com.example.data.model.product

import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.google.gson.annotations.SerializedName

data class ProductDto(

    @field:SerializedName("sold")
    val sold: Int? = null,

    @field:SerializedName("images")
    val images: List<String?>? = null,

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("availableColors")
    val availableColors: List<Any?>? = null,

    @field:SerializedName("imageCover")
    val imageCover: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("ratingsQuantity")
    val ratingsQuantity: Int? = null,

    @field:SerializedName("ratingsAverage")
    val ratingsAverage: Double? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("subcategory")
    val subcategory: List<SubCategory?>? = null,

    @field:SerializedName("category")
    val category: Category? = null,

    @field:SerializedName("brand")
    val brand: Brand? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("priceAfterDiscount")
    val priceAfterDiscount: Int? = null
) {
    fun toProduct(): Product {
        return Product(
            sold = sold,
            images = images,
            subcategory = subcategory,
            imageCover = imageCover,
            title = title,
            description = description,
            ratingsAverage = ratingsAverage,
            slug = slug,
            price = price,
            id = id,
            ratingsQuantity = ratingsQuantity,
            createdAt = createdAt,
            quantity = quantity,
            category = category,
            brand = brand
        )
    }
}