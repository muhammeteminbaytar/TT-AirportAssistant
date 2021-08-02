package com.muhammetbaytar.ttairportassistant.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammetbaytar.ttairportassistant.BuildConfig
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.adapter.AirlineAdapter
import com.muhammetbaytar.ttairportassistant.model.AirlinesContainer
import com.muhammetbaytar.ttairportassistant.service.AirlineCall
import kotlinx.android.synthetic.main.fragment_airlines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirlinesFragment : Fragment() {

    private var airlineRecy: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Airlines"

        return inflater.inflate(R.layout.fragment_airlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchJson()

        airlineRecy = view.findViewById(R.id.recylerViewAirline)
        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        airlineRecy?.layoutManager = gridLayoutManager


        

    }

    fun fetchJson() {

        val call = AirlineCall(BuildConfig.BASE_URL)

        call.getCallData().enqueue(object : Callback<AirlinesContainer> {
            override fun onResponse(
                call: Call<AirlinesContainer>,
                response: Response<AirlinesContainer>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        var airlineAdapter = AirlineAdapter(it.airlines)
                        airlineRecy?.adapter = airlineAdapter

                    }
                }
            }

            override fun onFailure(call: Call<AirlinesContainer>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.airline_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AirlineAdapter.selectQty = 0
        recylerViewAirline.adapter?.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

}