package com.example.labandroidapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter to display menu objects in recyclerView in UI
class MenuAdapter(private val menuItems: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>() {


    // viewHolder when scrolling
    inner class MenuItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.editTextMenuItemName)
        val priceTextView: TextView = view.findViewById(R.id.editTextMenuItemPrice)
    }

    //inflate layout and create viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuItemViewHolder(view)
    }

    // bind data to views in viewHolder
    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.nameTextView.text = menuItem.name
        holder.priceTextView.text = menuItem.price
    }

    //return nr of items in dataset
    override fun getItemCount() = menuItems.size
}
