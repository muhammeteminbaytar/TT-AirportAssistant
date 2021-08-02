package com.muhammetbaytar.ttairportassistant.view

import android.content.Intent
import android.os.Bundle
import com.muhammetbaytar.ttairportassistant.BuildConfig
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.model.FlightDetail
import com.muhammetbaytar.ttairportassistant.service.FlightDetailCall
import com.muhammetbaytar.ttairportassistant.view.base.BaseActivity
import com.muhammetbaytar.ttairportassistant.view.widget.CustomLoadDilaog
import kotlinx.android.synthetic.main.activity_flight_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightsDetailAct : BaseActivity() {

    val dilaog= CustomLoadDilaog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_detail)

        dilaog.createLoadDialog(this)

        runOnUiThread {
            title="Flights Detail"
        }

        fetchJson()
        initSensor()
    }

    fun fetchJson(){

        val intent: Intent = intent
        var id=intent.getStringExtra("id")

        val call=FlightDetailCall(BuildConfig.BASE_URL,id.toString())



        call.getCallGetData().enqueue(object : Callback<FlightDetail>{
            override fun onResponse(call: Call<FlightDetail>, response: Response<FlightDetail>) {
                    if(response.isSuccessful){
                        response.body()?.let {
                            putJsonVal(it)
                            dilaog.cancelLoadDialog()
                        }
                    }
            }

            override fun onFailure(call: Call<FlightDetail>, t: Throwable) {
                    t.printStackTrace()
            }
        })
    }

    fun putJsonVal(flightDetail: FlightDetail){
        runOnUiThread {

            detail_flightName.text=detail_flightName.text.toString()+" : "+flightDetail.flightName
            detail_flight_number.text=detail_flight_number.text.toString()+" : "+flightDetail.flightNumber
            detail_flightDirection.text=detail_flightDirection.text.toString()+" : "+flightDetail.flightDirection
            detail_gate.text=detail_gate.text.toString()+" : "+flightDetail.gate
            detail_pier.text=detail_pier.text.toString()+" : "+flightDetail.pier
            detail_scheduleDate.text=detail_scheduleDate.text.toString()+" : "+flightDetail.scheduleDate+" / "+flightDetail.scheduleTime
            detail_mainFlight.text= detail_mainFlight.text.toString()+" : "+flightDetail.mainFlight
            detail_airlineCode.text=detail_airlineCode.text.toString()+" : "+flightDetail.airlineCode
            detail_serviceType.text=detail_serviceType.text.toString()+" : "+flightDetail.serviceType

        }
    }
}