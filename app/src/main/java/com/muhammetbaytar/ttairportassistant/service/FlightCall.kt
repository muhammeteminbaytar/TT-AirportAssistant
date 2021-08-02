package com.muhammetbaytar.ttairportassistant.service

import com.muhammetbaytar.ttairportassistant.model.FlightContainer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlightCall(BASE_URL:String) {

    val retrofitModel=RetrofitModel(BASE_URL)

    val service=retrofitModel.getRetrofit().create(FlightAPI::class.java)

    fun getCallData():Call<FlightContainer>{
        val call=service.getData()
        return call
    }
}