package com.example.ecommerceapp.ui.tabs.profile.addresses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.addresses.Address
import com.example.ecommerceapp.databinding.ItemAddressBinding

class AddressesAdapter(var addresses: MutableList<Address?>?) :
    RecyclerView.Adapter<AddressesAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address?) {
            binding.address = address
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddressBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = addresses?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addresses?.get(position))
        holder.binding.deleteAddressImg.setOnClickListener {
            onAddressDeletedListener?.onAddressDeleted(addresses?.get(position)!!, position)
        }
    }

    fun bindAddresses(addresses: MutableList<Address?>?) {
        this.addresses = addresses
        notifyDataSetChanged()
    }


    fun removeAddress(position: Int) {
        addresses?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, addresses?.size ?: 0)
    }

    var onAddressDeletedListener: OnAddressDeletedListener? = null

    fun interface OnAddressDeletedListener {
        fun onAddressDeleted(address: Address, position: Int)
    }
}