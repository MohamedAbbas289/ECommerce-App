package com.example.ecommerceapp.ui.tabs.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.SubCategory
import com.example.ecommerceapp.databinding.ItemSubCategoryBinding

class SubCategoriesAdapter(var subCategories: List<SubCategory?>?) :
    RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemSubCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subCategory: SubCategory?) {
            binding.subCategory = subCategory
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSubCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = subCategories?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subCategories!![position])
        onItemClickListener?.let { clickListener ->
            holder.itemView.setOnClickListener {
                clickListener.onItemClick(position, subCategories!![position])
            }
        }
    }

    fun bindSubCategories(subCategories: List<SubCategory?>) {
        this.subCategories = subCategories
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: SubCategory?)
    }
}