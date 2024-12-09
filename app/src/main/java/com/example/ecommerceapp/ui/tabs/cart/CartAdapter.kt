package com.example.ecommerceapp.ui.tabs.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.cart.ProductsItem
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemCartBinding

class CartAdapter(
    var productsItems: MutableList<ProductsItem?>?
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productsItem: ProductsItem?) {
            Glide.with(binding.root.context)
                .load(productsItem?.product?.imageCover)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.productImage)
            binding.productTitle.text = productsItem?.product?.title
            binding.productPrice.text = "EGP " + (productsItem?.price?.times(productsItem?.count!!))
            binding.count.text = productsItem?.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productsItems?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productsItems?.get(position))
        holder.binding.removeBtn.setOnClickListener {
            onRemoveFromCartClickListener?.onRemoveFromCartClick(
                productsItems?.get(position)!!,
                position
            )
        }

        holder.binding.plusBtn.setOnClickListener {
            val productItem = productsItems?.get(position)
            productItem?.count = productItem?.count?.plus(1) // Increment the count locally
            notifyItemChanged(position)
            onPlusClickListener?.onPlusClick(
                productsItems?.get(position)!!,
                position
            )
        }

        holder.binding.minusBtn.setOnClickListener {
            val productItem = productsItems?.get(position)
            productItem?.count = productItem?.count?.minus(1) // decrement the count locally
            notifyItemChanged(position)
            onMinusClickListener?.onMinusClick(
                productsItems?.get(position)!!,
                position
            )
        }
    }

    fun bindProducts(products: MutableList<ProductsItem?>?) {
        productsItems = products
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        productsItems?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, productsItems?.size ?: 0)
    }

    fun clearProducts() {
        productsItems?.clear()
        notifyDataSetChanged()
    }

    fun updateItemCount(position: Int) {
        notifyItemChanged(position)
    }

    var onRemoveFromCartClickListener: OnRemoveFromCartClickListener? = null

    fun interface OnRemoveFromCartClickListener {
        fun onRemoveFromCartClick(productsItem: ProductsItem, position: Int)
    }

    var onPlusClickListener: OnPlusClickListener? = null

    fun interface OnPlusClickListener {
        fun onPlusClick(productsItem: ProductsItem, position: Int)
    }

    var onMinusClickListener: OnMinusClickListener? = null

    fun interface OnMinusClickListener {
        fun onMinusClick(productsItem: ProductsItem, position: Int)
    }

}