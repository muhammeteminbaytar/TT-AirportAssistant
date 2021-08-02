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
import com.muhammetbaytar.ttairportassistant.adapter.AirplanesAdapter
import com.muhammetbaytar.ttairportassistant.model.AircraftTypes
import com.muhammetbaytar.ttairportassistant.service.AirplaneCall
import com.muhammetbaytar.ttairportassistant.view.widget.CustomLoadDilaog
import kotlinx.android.synthetic.main.fragment_airplanes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AirplanesFragment : Fragment() {

    val dialog = CustomLoadDilaog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Airplanes"

        return inflater.inflate(R.layout.fragment_airplanes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyViewAirplane.layoutManager = LinearLayoutManager(view.context)

        fetchJson()

        this.activity?.let { dialog.createLoadDialog(it) }
    }

    fun fetchJson() {


        val call = AirplaneCall(BuildConfig.BASE_URL)

        call.getCallData().enqueue(object : Callback<AircraftTypes> {
            override fun onResponse(
                call: Call<AircraftTypes>,
                response: Response<AircraftTypes>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        recyViewAirplane.adapter = AirplanesAdapter(it.aircraftTypes)
                        dialog.cancelLoadDialog()
                    }
                }
            }

            override fun onFailure(call: Call<AircraftTypes>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}

