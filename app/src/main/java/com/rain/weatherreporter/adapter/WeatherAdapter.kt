package com.rain.weatherreporter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rain.weatherreporter.DetailsActivity
import com.rain.weatherreporter.MainActivity
import com.rain.weatherreporter.R
import kotlinx.android.synthetic.main.city.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    var cityList = mutableListOf<String>()
    val context: Context



    constructor(context: Context, listCities: List<String>) {
        this.context = context
        cityList.addAll(listCities)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.city, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = cityList[position]

        holder.btnDelete?.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }

        holder.btnDetails?.setOnClickListener {
                val myIntent = Intent(context, DetailsActivity::class.java)
                myIntent.putExtra("cityName", currentCity)
                ContextCompat.startActivity(context, myIntent, null)
        }
    }

    private fun deleteItem(position: Int) {
        cityList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(name: String) {
        cityList.add(name)
        notifyItemInserted(cityList.lastIndex)
    }

    inner class ViewHolder(cityView: View) : RecyclerView.ViewHolder(cityView) {
        val cityName = cityView.tvCityName
        val btnDelete = cityView.btnDelete
        val btnDetails = cityView.btnDetails
    }
}