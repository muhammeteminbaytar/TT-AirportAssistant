package com.muhammetbaytar.ttairportassistant.service
import com.muhammetbaytar.ttairportassistant.model.AircraftTypes
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirplaneCall(BASE_URL:String) {

    val retrofitModel=RetrofitModel(BASE_URL)

    val service=retrofitModel.getRetrofit().create(AirplaneAPI::class.java)

    fun getCallData(): Call<AircraftTypes> {
        val call = service.getData()
        return call
    }
}