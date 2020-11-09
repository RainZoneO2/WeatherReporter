package com.rain.weatherreporter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rain.weatherreporter.DetailsActivity
import com.rain.weatherreporter.MainActivity
import com.rain.weatherreporter.R
import kotlinx.android.synthetic.main.city.view.*
import kotlinx.android.synthetic.main.city_dialog.view.*

class WeatherAdapter(val context: Context) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    var cityList = mutableListOf<String>()

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
//        val Unit = {
//            if
//        }
        holder.cityName.text = currentCity

        holder.btnDelete?.setOnClickListener {
            deleteCity(holder.adapterPosition)
        }

        holder.btnDetails?.setOnClickListener {
            val myIntent = Intent(context, DetailsActivity::class.java)
            myIntent.putExtra("cityName", currentCity)
            myIntent.putExtra("unit", "metric")
            context.startActivity(myIntent)
        }
    }

    private fun deleteCity(position: Int) {
        (context as MainActivity).runOnUiThread {
            cityList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addCity(name: String) {
        cityList.add(name)
        notifyItemInserted(cityList.lastIndex)
    }

    inner class ViewHolder(cityView: View) : RecyclerView.ViewHolder(cityView) {
        val cityName = cityView.tvCityName
        val unit = cityView.swMetImper
        val btnDelete = cityView.btnDelete
        val btnDetails = cityView.btnDetails
    }
}