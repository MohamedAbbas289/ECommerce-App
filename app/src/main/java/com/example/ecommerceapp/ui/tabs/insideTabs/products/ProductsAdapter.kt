package com.example.ecommerceapp.ui.tabs.insideTabs.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Product
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemProductBinding

class ProductsAdapter(var products: List<Product?>?) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product?) {
            if (product?.isInWishlist == true) {
                binding.addWishList.setImageResource(R.drawable.ic_added_to_wishlist)
            } else {
                binding.addWishList.setImageResource(R.drawable.ic_add_to_wishlist)
            }
            binding.product = product
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = products?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products!![position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, products!![position])
        }
        holder.binding.addWishList.setOnClickListener {
            onAddToWishlistClickListener?.onAddToWishlistClick(position, products!![position])
            products!![position]?.isInWishlist = true
            notifyItemChanged(position)
        }

    }

    fun bindProducts(products: List<Product?>) {
        this.products = products
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, product: Product?)
    }

    var onAddToWishlistClickListener: OnAddToWishlistClickListener? = null

    fun interface OnAddToWishlistClickListener {
        fun onAddToWishlistClick(position: Int, product: Product?)
    }
}