package com.rain.weatherreporter

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.rain.weatherreporter.data.WeatherResult
import com.rain.weatherreporter.network.WeatherAPI
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var imageView: ImageView
    private var appID : String = "db810cb02a6ad86e545cfed55209e13e"
    lateinit var weatherAPI: WeatherAPI

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_details)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_addcity, R.id.nav_about), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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
                text_home.setText(t.message)
            }

            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                val weatherResult = response.body()
                Glide.with(this@DetailsActivity).load("http://openweathermap.org/img/w/"
                        + weatherResult?.weather?.get(0)?.icon+".png").into(imageView)
            }
        })
    }
}