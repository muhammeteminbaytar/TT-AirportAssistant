package com.muhammetbaytar.ttairportassistant.service

import com.muhammetbaytar.ttairportassistant.model.AirPlanes
import com.muhammetbaytar.ttairportassistant.model.AircraftTypes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface AirplaneAPI {
    @Headers(
        "Accept: application/json",
        "app_id: 1982276f",
        "app_key: 599c014f3c98201693620ed0359eb3c6",
        "ResourceVersion: v4"
    )

    @GET("aircrafttypes?page=0&sort=%2BiataMain")

    fun getData(): Call<AircraftTypes>
}