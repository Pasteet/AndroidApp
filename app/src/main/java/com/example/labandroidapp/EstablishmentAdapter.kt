package com.example.labandroidapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter to display establishment objects in recyclerView in UI
class EstablishmentAdapter(private val establishments: List<Establishment>) :
    RecyclerView.Adapter<EstablishmentAdapter.EstablishmentViewHolder>() {

        // viewHolder when scrolling
    inner class EstablishmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.establishmentName)
        val addressTextView: TextView = view.findViewById(R.id.establishmentAddress)
        val typeTextView: TextView = view.findViewById(R.id.establishmentType)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context = view.context
                    val intent = Intent(context, EstablishmentMenuActivity::class.java).apply {
                        putExtra("establishment", establishments[position])
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    //inflate layout and create viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstablishmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_establishment, parent, false)
        return EstablishmentViewHolder(view)
    }

    // bind data to views in viewHolder
    override fun onBindViewHolder(holder: EstablishmentViewHolder, position: Int) {
        val establishment = establishments[position]
        holder.nameTextView.text = establishment.name
        holder.addressTextView.text = establishment.address
        holder.typeTextView.text = establishment.type
    }

    //return nr of items in dataset
    override fun getItemCount() = establishments.size
}
