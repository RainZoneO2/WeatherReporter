package com.rain.weatherreporter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.rain.weatherreporter.adapter.WeatherAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    CityDialog.CityHandler {

    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            showAddCityDialog()
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        weatherAdapter = WeatherAdapter(this)
        recyclerList.adapter = weatherAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun showAddCityDialog() {
        CityDialog().show(supportFragmentManager, "Dialog")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                //true
                false
            }
            R.id.nav_addcity -> {
                showAddCityDialog()
                //false
            }
            R.id.nav_about -> {
                Toast.makeText(this, "Alghaith Ahmad - S8912J", Toast.LENGTH_SHORT).show()
                false
            }
            else -> false
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun createCity(cityName: String) {
        runOnUiThread {
            recyclerList.adapter = weatherAdapter
            weatherAdapter.addCity(cityName)
        }
    }

    override fun cityCreated(city: String) {
        createCity(city)
    }
}