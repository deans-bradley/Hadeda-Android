package com.st10083210.hadeda.ui.BirdMap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.st10083210.hadeda.R

class HotspotAdapter(private val mList: List<HotspotViewModel>) : RecyclerView.Adapter<HotspotAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotspot_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val HotspotViewModel = mList[position]

        holder.name.text = HotspotViewModel.Name
        holder.location.text = HotspotViewModel.Location
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.hotspot_name_tv)
        val location: TextView = itemView.findViewById(R.id.hotspot_location_tv)
    }
}