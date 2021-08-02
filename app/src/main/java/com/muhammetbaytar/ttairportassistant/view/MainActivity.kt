package com.muhammetbaytar.ttairportassistant.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.muhammetbaytar.ttairportassistant.R
import com.muhammetbaytar.ttairportassistant.view.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Handler(Looper.getMainLooper()).postDelayed({

            val mainIntent = Intent(this, LoginScreen::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)
    }
}