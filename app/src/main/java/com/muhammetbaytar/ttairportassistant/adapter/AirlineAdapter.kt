package com.muhammetbaytar.ttairportassistant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.adapter.AirlineAdapter.Companion.selectQty
import com.muhammetbaytar.ttairportassistant.model.AirlinesModel
import kotlinx.android.synthetic.main.item_airline.view.*

class AirlineAdapter(
    private val airlineList: List<AirlinesModel>
) : RecyclerView.Adapter<ViewHolder>() {
    companion object {
        var selectQty = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_airline, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val airline = airlineList[position]
        holder.itemView.txt_airline_name.text = airline.publicName
        holder.itemView.txt_airline_iata.text = airline.iata
        if (airline.iata == null) {
            holder.itemView.txt_airline_iata.text = "N/A"
        }
    }

    override fun getItemCount(): Int {
        return airlineList.count()
    }


}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {


            if (itemView.txt_airline_iata.text == "N/A") {

                val builder = AlertDialog.Builder(itemView.context)
                builder.setTitle("Warning")
                builder.setMessage("This item cannot be selected.")

                builder.setPositiveButton("Ok") { dialog, which ->
                }

                builder.show()
            } else {
                if (selectQty > 10) {
                    val builder = AlertDialog.Builder(itemView.context)
                    builder.setTitle("Warning")
                    builder.setMessage("You can choose up to 10")

                    builder.setPositiveButton("Ok") { dialog, which ->
                    }

                    builder.show()
                } else {
                    if (itemView.chk_airline.isChecked) {
                        itemView.chk_airline.isVisible = false
                        itemView.chk_airline.isChecked = false
                        selectQty -= 1

                    } else {
                        itemView.chk_airline.isVisible = true
                        itemView.chk_airline.isChecked = true
                        selectQty += 1
                    }
                }
            }
        }
    }
}