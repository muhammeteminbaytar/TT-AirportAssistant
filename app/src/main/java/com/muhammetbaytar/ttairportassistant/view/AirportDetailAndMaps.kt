package com.muhammetbaytar.ttairportassistant.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.adapter.map_adapter.CustomInfoWindowAdapter
import com.muhammetbaytar.ttairportassistant.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_airport_detail_and_maps.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AirportDetailAndMaps : BaseActivity(), OnMapReadyCallback {

    lateinit var AirportName: String
    lateinit var Lat: String
    lateinit var Long: String
    lateinit var CountryName: String
    lateinit var CountryIso: String
    lateinit var TimeZone: String
    lateinit var IataCode: String

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airport_detail_and_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        title = "Airport Detail"
        getIntentData()
        initSensor()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val intent: Intent = intent
        Lat = intent.getStringExtra("Lat").toString()
        Long = intent.getStringExtra("Long").toString()
        AirportName = intent.getStringExtra("AirportName").toString()
        CountryName = intent.getStringExtra("CountryName").toString()
        CountryIso = intent.getStringExtra("CountryIso").toString()


        val mapLocation = LatLng(Lat.toDouble(), Long.toDouble())
        mMap.clear()
        mMap.setInfoWindowAdapter(
            CustomInfoWindowAdapter(
                this,
                AirportName,
                CountryName,
                CountryIso
            )
        )
        mMap.addMarker(
            MarkerOptions()
                .position(mapLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))
        ).showInfoWindow()
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mapLocation, 15f))

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()

            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true


    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    fun getIntentData() {
        val intent: Intent = intent
        AirportName = intent.getStringExtra("AirportName").toString()
        Lat = intent.getStringExtra("Lat").toString()
        Long = intent.getStringExtra("Long").toString()
        CountryName = intent.getStringExtra("CountryName").toString()
        CountryIso = intent.getStringExtra("CountryIso").toString()
        TimeZone = intent.getStringExtra("TimeZone").toString()
        IataCode = intent.getStringExtra("IataCode").toString()

        fillInTheDetailText(AirportName, Lat, Long, CountryName, CountryIso, TimeZone, IataCode)

    }

    fun fillInTheDetailText(
        AirportName: String, Lat: String, Long: String, CountryName: String,
        CountryIso: String, TimeZone: String, IataCode: String
    ) {
        txt_airport_name.text = txt_airport_name.text.toString() + " : " + AirportName
        txt_airport_country_name.text =
            txt_airport_country_name.text.toString() + " : " + CountryName
        txt_airport_timezone.text = txt_airport_timezone.text.toString() + " : " + TimeZone
        txt_airport_lat.text = txt_airport_lat.text.toString() + " : " + Lat
        txt_airport_long.text = txt_airport_long.text.toString() + " : " + Long
        txt_airport_country_iso.text = txt_airport_country_iso.text.toString() + " : " + CountryIso
        txt_airport_iata_code.text = txt_airport_iata_code.text.toString() + " : " + IataCode
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.airport_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            var navigationUrl =
                "https://maps.google.com/?q=$Lat,$Long"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT, " Airport Name : " + AirportName +
                        "\n Country Name : " + CountryName + "\n Navigation Url : " + navigationUrl
            );
            startActivity(Intent.createChooser(shareIntent, "Share This Airport"))
        } else if (item.itemId == R.id.navigate) {
            val googleMapsIntentUri = Uri.parse("google.navigation:q=$Lat,$Long")
            val mapIntent = Intent(Intent.ACTION_VIEW, googleMapsIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}