package com.muhammetbaytar.ttairportassistant.service

import android.content.Intent
import android.telecom.Call
import com.muhammetbaytar.ttairportassistant.model.FlightDetail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlightDetailCall(BASE_URL:String,id: String) {

    var id = id

    val retrofitModel=RetrofitModel(BASE_URL)

    val service = retrofitModel.getRetrofit().create(FlightDetailAPI::class.java)

    fun getCallGetData():retrofit2.Call<FlightDetail> {
        val call = service.getData(id)
        return call
    }
}