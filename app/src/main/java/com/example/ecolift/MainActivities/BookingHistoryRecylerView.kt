package com.example.ecolift.MainActivities

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecolift.Data_Classes.AllPostedRide
import com.example.ecolift.Data_Classes.CreateTravllerResponse
import com.example.ecolift.R

class BookingHistoryRecylerView: RecyclerView.Adapter<BookingHistoryRecylerView.PostListAdapter>() {

    private val allPostRideList = ArrayList<CreateTravllerResponse>()

    inner class PostListAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {

        val pickup: TextView = itemView.findViewById(R.id.pickup_name)
        val destination:TextView = itemView.findViewById(R.id.destination_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter {
        val viewHolder = PostListAdapter(LayoutInflater.from(parent.context).inflate(R.layout.booked_history_recylerview_format,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder:PostListAdapter, position: Int) {
        val positionOnScreen = allPostRideList[position]
        holder.pickup.text = "Pickup: " + positionOnScreen.pickup
        holder.destination.text = "Destination: " +   positionOnScreen.destination
    }

    override fun getItemCount(): Int {
        return allPostRideList.size
    }

    fun updateAll(itemList:List<CreateTravllerResponse>){
        allPostRideList.clear()
        Log.d("succes it",itemList.toString())
        allPostRideList.addAll(itemList)
        notifyDataSetChanged()
    }

}