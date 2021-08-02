package com.muhammetbaytar.ttairportassistant.service

import com.muhammetbaytar.ttairportassistant.model.AirportContainer
import retrofit2.Call
import retrofit2.http.GET

interface AirportAPI {
    //http://api.aviationstack.com/v1/airports?access_key=ede286e03ec6e0b5f1afb36bba825da2&offset=0
    @GET("airports?access_key=ede286e03ec6e0b5f1afb36bba825da2&offset=0")

    fun getData(): Call<AirportContainer>
}