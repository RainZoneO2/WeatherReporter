package com.rain.weatherreporter.network

import com.rain.weatherreporter.data.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//HOST: https://openweathermap.org/api
//
//PATH: data/2.5/weather
//
//QUERY ARGS:
// q[=Budapest]
// units[=metric]
// appid[=xxx]

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getWeather(@Query("q") q : String,
                   @Query("units") units : String,
                   @Query("appid") appid : String
    ) : Call<WeatherResult>
}