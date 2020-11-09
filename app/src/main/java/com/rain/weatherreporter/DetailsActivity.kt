package com.rain.weatherreporter

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rain.weatherreporter.data.WeatherResult
import com.rain.weatherreporter.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_scrolling.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    private var appID : String = "db810cb02a6ad86e545cfed55209e13e"
    lateinit var weatherAPI: WeatherAPI

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherAPI = retrofit.create(WeatherAPI::class.java)
        imageView = findViewById(R.id.ivIcon)

        val cityName = intent.getStringExtra("cityName")
        val unit = intent.getStringExtra("unit")
        getAPIData(cityName, unit)
    }

    fun getAPIData(cityName: String, unit: String) {
        val weatherCall = weatherAPI.getWeather(cityName, unit, appID)

        weatherCall.enqueue(object : Callback<WeatherResult> {
            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(this@DetailsActivity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                val weatherResult = response.body()
                Glide.with(this@DetailsActivity).load("http://openweathermap.org/img/w/"
                        + weatherResult?.weather?.get(0)?.icon+".png").into(imageView)


                tvCoord.text = "${weatherResult?.coord?.lon}" + "${weatherResult?.coord?.lat}"
                tvDescription.text = "${weatherResult?.weather?.get(0)?.description}"
                tvHumid.text = "${weatherResult?.main?.humidity}"
                tvMaxTemp.text = "${weatherResult?.main?.temp_max}"
                tvMinTemp.text = "${weatherResult?.main?.temp_min}"
                tvPressure.text = "${weatherResult?.main?.pressure}"
                tvTemp.text = "${weatherResult?.main?.temp}"
                tvWeather.text = "${weatherResult?.weather?.get(0)?.main}"
            }
        })
    }
}