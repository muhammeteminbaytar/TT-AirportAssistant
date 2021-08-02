package com.muhammetbaytar.ttairportassistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammetbaytar.ttairportassistant.BuildConfig
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.adapter.FlightsAdapter
import com.muhammetbaytar.ttairportassistant.model.FlightContainer
import com.muhammetbaytar.ttairportassistant.service.FlightCall
import com.muhammetbaytar.ttairportassistant.view.widget.CustomLoadDilaog
import kotlinx.android.synthetic.main.fragment_flights.*
import retrofit2.Call


class FlightsFragment : Fragment() {

    val dialog= CustomLoadDilaog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Flights"

        return inflater.inflate(R.layout.fragment_flights, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.activity?.let { dialog.createLoadDialog(it) }

        recyViewFlights.layoutManager= LinearLayoutManager(view?.context)
        fetchJson()

    }

    fun fetchJson(){

        val call=FlightCall(BuildConfig.BASE_URL)

        call.getCallData().enqueue(object : retrofit2.Callback<FlightContainer> {
            override fun onResponse(call: Call<FlightContainer>, response: retrofit2.Response<FlightContainer>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        recyViewFlights.adapter = FlightsAdapter(it.flights)
                        dialog.cancelLoadDialog()
                    }
                }
            }
            override fun onFailure(call: Call<FlightContainer>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}