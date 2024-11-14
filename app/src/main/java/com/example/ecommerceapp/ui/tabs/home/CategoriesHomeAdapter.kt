package com.example.ecommerceapp.ui.tabs.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Category
import com.example.ecommerceapp.databinding.ItemCategoryHomeBinding

class CategoriesHomeAdapter(
    var categories: List<Category?>?
) : RecyclerView.Adapter<CategoriesHomeAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemCategoryHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category?) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categories?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories!![position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, categories!![position])
        }
    }

    fun bindCategories(categories: List<Category?>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, category: Category?)
    }
}