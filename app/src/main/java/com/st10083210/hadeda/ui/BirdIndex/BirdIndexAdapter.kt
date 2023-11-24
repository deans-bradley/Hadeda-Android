package com.st10083210.hadeda.ui.BirdIndex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.st10083210.hadeda.R

class BirdIndexAdapter(private val mList: ArrayList<BirdIndexModel>) : RecyclerView.Adapter<BirdIndexAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bird_index_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val indexModel = mList[position]

        holder.comNameTv.text = indexModel.commonName
        holder.sciNameTv.text = indexModel.scientificName
        holder.locationTv.text = indexModel.location
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val comNameTv: TextView = itemView.findViewById(R.id.tv_common_name)
        val sciNameTv: TextView = itemView.findViewById(R.id.tv_scientific_name)
        val locationTv: TextView = itemView.findViewById(R.id.tv_location)
    }
}