package com.muhammetbaytar.ttairportassistant.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModel(BASE_URL:String) {

    val BASE_URL=BASE_URL
    fun getRetrofit():Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}