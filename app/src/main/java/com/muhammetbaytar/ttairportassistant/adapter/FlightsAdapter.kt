package com.muhammetbaytar.ttairportassistant.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.fragment.FlightsFragment
import com.muhammetbaytar.ttairportassistant.model.Flight
import com.muhammetbaytar.ttairportassistant.view.FlightsDetailAct
import kotlinx.android.synthetic.main.item_flights.view.*

class FlightsAdapter(val flightsList: List<Flight>) : RecyclerView.Adapter<CustomFlightsViewHolder>() {



    override fun getItemCount(): Int = flightsList.count()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomFlightsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_flights, parent, false)
        return CustomFlightsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomFlightsViewHolder, position: Int) {
        val flightsList = flightsList[position]
        holder.itemView.flightsRowFname.text = flightsList.flightName
        holder.itemView.flightsRowFgate.text = flightsList.gate
        holder.itemView.flightsRowFaircraft.text = flightsList.aircraftRegistration
        holder.itemView.flightsRowFdate.text = flightsList.scheduleDate + " / " + flightsList.scheduleTime
        holder.id = flightsList.id
    }
}

class CustomFlightsViewHolder(view: View, var id: String? = null) : RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, FlightsDetailAct::class.java)
            intent.putExtra("id", id)
            view.context.startActivity(intent)

        }
    }
}