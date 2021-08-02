package com.muhammetbaytar.ttairportassistant.adapter.map_adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.muhammetbaytar.ttairportassistant.R
import kotlinx.android.synthetic.main.custom_info_window.view.*

class CustomInfoWindowAdapter(context: Context,airportName:String,countryName:String,countryIso:String):GoogleMap.InfoWindowAdapter {

    var mContext = context
    var airportName=airportName
    var countryName=countryName
    var countryIso=countryIso

    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.custom_info_window, null)

    private fun rendowWindowText(marker: Marker, view: View){
        view.txt_custom_info_title.text=airportName
        view.txt_custom_info_country.text=countryName + " / "+countryIso
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker,mWindow)
        return mWindow
    }

    override fun getInfoContents(marker: Marker): View? {
        rendowWindowText(marker,mWindow)
        return mWindow
    }



}