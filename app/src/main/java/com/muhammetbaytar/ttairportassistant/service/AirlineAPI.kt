package com.muhammetbaytar.ttairportassistant.service

import com.muhammetbaytar.ttairportassistant.model.AircraftTypes
import com.muhammetbaytar.ttairportassistant.model.AirlinesContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AirlineAPI {
    @Headers(
        "Accept: application/json",
        "app_id: 1982276f",
        "app_key: 599c014f3c98201693620ed0359eb3c6",
        "ResourceVersion: v4"
    )
    @GET("airlines?page=0&sort=%2BpublicName")

    fun getData(): Call<AirlinesContainer>

}