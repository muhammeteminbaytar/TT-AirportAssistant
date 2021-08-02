package com.muhammetbaytar.ttairportassistant.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.model.AirportModel
import com.muhammetbaytar.ttairportassistant.view.AirportDetailAndMaps
import kotlinx.android.synthetic.main.item_destination.view.*

class AirportAdapter(val airportList:List<AirportModel>): RecyclerView.Adapter<CustomAirportViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAirportViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_destination, parent, false)
        return CustomAirportViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomAirportViewHolder, position: Int) {
        val airport=airportList[position]
        holder.view.detail_flight_number.text=airport.airport_name
        holder.view.txt_airport_country.text=airport.country_name
        holder.view.txt_airport_timezone.text=airport.timezone

        val imageAirpots= arrayListOf(R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10)
        holder.view.iw_airtport.setImageResource(imageAirpots[position%10])

        holder.AirportName=airport.airport_name
        holder.CountryName=airport.country_name
        holder.Lat=airport.latitude
        holder.Long=airport.longitude
        holder.TimeZone=airport.timezone
        holder.IataCode=airport.iata_code
        holder.CountryIso=airport.country_iso2
    }

    override fun getItemCount(): Int {
        return airportList.count()
    }
}
class CustomAirportViewHolder(val view:View,var AirportName:String?=null,
var Lat:String?=null, var Long:String?=null,var CountryName:String?=null,var CountryIso:String?=null,
var TimeZone:String?=null,var IataCode:String?=null):RecyclerView.ViewHolder(view){
    init {
        view.setOnClickListener{
            val intent=Intent(view.context,AirportDetailAndMaps::class.java)
            intent.putExtra("AirportName",AirportName)
            intent.putExtra("Lat",Lat)
            intent.putExtra("Long",Long)
            intent.putExtra("CountryName",CountryName)
            intent.putExtra("CountryIso",CountryIso)
            intent.putExtra("TimeZone",TimeZone)
            intent.putExtra("IataCode",IataCode)
            view.context.startActivity(intent)
        }
    }
}