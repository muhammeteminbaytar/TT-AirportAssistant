package com.muhammetbaytar.ttairportassistant.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.fragment.*
import com.muhammetbaytar.ttairportassistant.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        fragmentCreater()
    }

    private fun fragmentCreater(){

        val airplanesFragment=AirplanesFragment()
        val airlinesFragment=AirlinesFragment()
        val destinationFragment=DestinationFragment()
        val flightsFragment=FlightsFragment()
        val profileFragment=ProfileFragment()

        makeCurrentFragment(airplanesFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_airplanes -> makeCurrentFragment(airplanesFragment)
                R.id.ic_airlines -> makeCurrentFragment(airlinesFragment)
                R.id.ic_destination ->makeCurrentFragment(destinationFragment)
                R.id.ic_flights ->makeCurrentFragment(flightsFragment)
                R.id.ic_profile ->makeCurrentFragment(profileFragment)
            }
            true
        }
    }
    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_wrapper,fragment)
            commit()
        }
    }
}