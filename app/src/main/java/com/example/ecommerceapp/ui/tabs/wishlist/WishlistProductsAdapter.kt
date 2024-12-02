package com.example.ecommerceapp.ui.tabs.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Product
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemWishlistProductBinding

class WishlistProductsAdapter(var products: MutableList<Product?>?) :
    RecyclerView.Adapter<WishlistProductsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemWishlistProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product?) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWishlistProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = products?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products?.get(position))
        holder.binding.wishlistImg.setOnClickListener {
            onRemoveFromWishlistClickListener?.onRemoveFromWishlistClick(
                products?.get(position)!!,
                position
            )
        }
    }

    fun bindWishlistProducts(wishlistProducts: MutableList<Product?>?) {
        this.products = wishlistProducts
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        products?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, products?.size ?: 0)
    }


    var onRemoveFromWishlistClickListener: OnRemoveFromWishlistClickListener? = null

    fun interface OnRemoveFromWishlistClickListener {
        fun onRemoveFromWishlistClick(product: Product, position: Int)
    }


}