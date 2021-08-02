package com.muhammetbaytar.ttairportassistant.service

import com.muhammetbaytar.ttairportassistant.model.AirlinesContainer
import retrofit2.Call

class AirlineCall(BASE_URL:String) {

    val retrofitModel=RetrofitModel(BASE_URL)

    val service=retrofitModel.getRetrofit().create(AirlineAPI::class.java)

    fun getCallData(): Call<AirlinesContainer> {
        val call = service.getData()
        return call
    }
}