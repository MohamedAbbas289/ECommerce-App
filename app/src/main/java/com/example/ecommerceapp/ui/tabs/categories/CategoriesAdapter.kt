package com.example.ecommerceapp.ui.tabs.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Category
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemCategoryBinding

class CategoriesAdapter(var categories: List<Category?>?) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category?, selected: Boolean) {
            binding.category = category
            if (selected) {
                binding.selectionBar.visibility = View.VISIBLE
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.white)
                )
            } else {
                binding.selectionBar.visibility = View.INVISIBLE
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.transparent)
                )
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categories?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories!![position], selectedItemPosition == position)
        onItemClickListener?.let { clickListener ->
            holder.itemView.setOnClickListener {
                notifyItemChanged(selectedItemPosition)
                selectedItemPosition = position
                notifyItemChanged(position)
                clickListener.onItemClick(position, categories!![position])
            }
        }
    }

    private var selectedItemPosition = 0
    fun bindCategories(categories: List<Category?>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: Category?)
    }
}