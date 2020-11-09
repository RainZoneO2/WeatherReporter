package com.rain.weatherreporter

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rain.weatherreporter.data.WeatherResult
import com.rain.weatherreporter.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var imageView: ImageView
    private var appID: String = "db810cb02a6ad86e545cfed55209e13e"
    lateinit var weatherAPI: WeatherAPI
    lateinit var gMap: GoogleMap
    lateinit var latLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherAPI = retrofit.create(WeatherAPI::class.java)
        imageView = findViewById(R.id.ivIcon)

        val cityName = intent.getStringExtra("cityName")
        val unit = intent.getStringExtra("unit")
        getAPIData(cityName, unit)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        latLng = LatLng(47.9, 19.04)

    }

    fun getAPIData(cityName: String, unit: String) {
        val weatherCall = weatherAPI.getWeather(cityName, unit, appID)

        weatherCall.enqueue(object : Callback<WeatherResult> {
            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(this@DetailsActivity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                val weatherResult = response.body()
                Glide.with(this@DetailsActivity).load(
                    "https://openweathermap.org/img/w/"
                            + weatherResult?.weather?.get(0)?.icon + ".png"
                ).into(imageView)
                latLng = LatLng(weatherResult?.coord?.lon!!, weatherResult.coord.lat)


                tvCoord.text = "Lat: ${weatherResult.coord?.lat}" + " Lon: ${weatherResult.coord?.lon}"
                tvDescription.text = "${weatherResult.weather?.get(0)?.description}"
                tvHumid.text = "${weatherResult.main?.humidity}"
                tvMaxTemp.text = "${weatherResult.main?.temp_max}"
                tvMinTemp.text = "${weatherResult.main?.temp_min}"
                tvPressure.text = "${weatherResult.main?.pressure}"
                tvTemp.text = "${weatherResult.main?.temp}"
                tvWeather.text = "${weatherResult.weather?.get(0)?.main}"
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        var zoom = 7f
        gMap.addMarker(MarkerOptions().position(latLng).title("Marker in current city"))
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }
}