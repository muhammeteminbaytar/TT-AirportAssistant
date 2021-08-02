package com.muhammetbaytar.ttairportassistant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.model.AirPlanes
import kotlinx.android.synthetic.main.item_airplane.view.*

class AirplanesAdapter(val aircraftTypesList: List<AirPlanes>) : RecyclerView.Adapter<CustomAirplaneViewHolder>() {


    override fun getItemCount(): Int {
        return aircraftTypesList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAirplaneViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_airplane, parent, false)
        return CustomAirplaneViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomAirplaneViewHolder, position: Int) {
        val airplane = aircraftTypesList[position]
        holder.view.airplaneRowPname.text = airplane.longDescription
        holder.view.airplaneRowıIata.text = airplane.iataMain
        var planeList = arrayListOf(R.drawable.plane1, R.drawable.plane2, R.drawable.plane3, R.drawable.plane4, R.drawable.plane5, R.drawable.plane6, R.drawable.plane7, R.drawable.plane8, R.drawable.plane9, R.drawable.plane10)

        holder.view.airplaneRowIw.setImageResource(planeList[position % 10])
    }
}

class CustomAirplaneViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}