package com.muhammetbaytar.ttairportassistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammetbaytar.ttairportassistant.BuildConfig
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.adapter.AirportAdapter
import com.muhammetbaytar.ttairportassistant.model.AirportContainer
import com.muhammetbaytar.ttairportassistant.service.AirportCall
import com.muhammetbaytar.ttairportassistant.view.widget.CustomLoadDilaog
import kotlinx.android.synthetic.main.fragment_destination.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationFragment : Fragment() {

    val dialog = CustomLoadDilaog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Destinations"
        return inflater.inflate(R.layout.fragment_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recylerView_airport.layoutManager = LinearLayoutManager(view?.context)
        this.activity?.let { dialog.createLoadDialog(it) }
        fetchJson()
    }

    fun fetchJson() {


        val call = AirportCall(BuildConfig.BASE_URL_Airport)
        call.getCallData().enqueue(object : Callback<AirportContainer> {
            override fun onResponse(
                call: Call<AirportContainer>,
                response: Response<AirportContainer>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        recylerView_airport.adapter = AirportAdapter(it.data)
                        dialog.cancelLoadDialog()
                    }
                }
            }

            override fun onFailure(call: Call<AirportContainer>, t: Throwable) {
                println(t.message)
            }
        })

    }

}