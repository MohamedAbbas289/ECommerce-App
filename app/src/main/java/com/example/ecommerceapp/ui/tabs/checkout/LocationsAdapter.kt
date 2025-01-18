package com.example.ecommerceapp.ui.tabs.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.addresses.Address
import com.example.ecommerceapp.databinding.ItemLocationSelectBinding

class LocationsAdapter(
    private var addresses: List<Address?>?
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private var selectedPosition: Int =
        if (addresses.isNullOrEmpty()) RecyclerView.NO_POSITION else 0

    class ViewHolder(val binding: ItemLocationSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address?, isSelected: Boolean, onClick: () -> Unit) {
            binding.address = address
            binding.radioBtn.isChecked = isSelected // update RadioButton state
            binding.root.setOnClickListener { onClick() }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLocationSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = addresses?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = addresses?.get(position)
        val isSelected = position == selectedPosition
        holder.bind(address, isSelected) {
            val previousPosition = selectedPosition
            selectedPosition = position

            onAddressSelectedListener?.onAddressSelected(address)

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    fun bindAddresses(addresses: List<Address?>?) {
        this.addresses = addresses
        selectedPosition = if (!addresses.isNullOrEmpty()) 0 else RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }


    var onAddressSelectedListener: OnAddressSelectedListener? = null

    fun interface OnAddressSelectedListener {
        fun onAddressSelected(address: Address?)
    }
}
