package com.muhammetbaytar.ttairportassistant.service

import com.muhammetbaytar.ttairportassistant.model.AircraftTypes
import com.muhammetbaytar.ttairportassistant.model.AirportContainer
import com.muhammetbaytar.ttairportassistant.model.AirportModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirportCall(BASE_URL:String) {

    val retrofitModel=RetrofitModel(BASE_URL)


    val service=retrofitModel.getRetrofit().create(AirportAPI::class.java)

    fun getCallData(): Call<AirportContainer> {
        val call = service.getData()
        return call
    }
}