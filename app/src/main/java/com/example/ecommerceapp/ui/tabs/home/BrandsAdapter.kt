package com.example.ecommerceapp.ui.tabs.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Brand
import com.example.ecommerceapp.databinding.ItemBrandBinding

class BrandsAdapter(
    var brands: List<Brand?>?
) : RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemBrandBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: Brand?) {
            binding.brand = brand
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBrandBinding
            .inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = brands?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(brands!![position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, brands!![position])
        }
    }

    fun bindBrands(brands: List<Brand?>) {
        this.brands = brands
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, brand: Brand?)
    }

}