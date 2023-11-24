package com.st10083210.hadeda.ui.BirdCollection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.st10083210.hadeda.R
import com.st10083210.hadeda.ui.AddBird.BirdModel

class BirdCollectionAdapter(private val mList: ArrayList<BirdModel>) : RecyclerView.Adapter<BirdCollectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bird_collection_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val birdModel = mList[position]

        //holder.imageView.setImageURI(birdModel.taskImages)
        // sets the text to the textview from our itemHolder class
        holder.nameTextView.text = birdModel.sBirdName
        holder.locationTextView.text= birdModel.sLocation

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
       // val imageView: ImageView = itemView.findViewById(R.id.birdImg)
        val nameTextView: TextView = itemView.findViewById(R.id.name_tv)
        val locationTextView: TextView = itemView.findViewById(R.id.location_tv)

    }
}