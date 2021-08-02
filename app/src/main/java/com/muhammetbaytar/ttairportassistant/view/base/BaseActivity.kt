package com.muhammetbaytar.ttairportassistant.view.base

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.muhammetbaytar.ttairportassistant.BuildConfig
import com.muhammetbaytar.ttairportassistant.shake.OnShakeListener
import com.muhammetbaytar.ttairportassistant.shake.ShakeDetector

abstract class BaseActivity:AppCompatActivity() {
    //for sensor control
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeDetector: ShakeDetector? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    fun initSensor(){

        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector?.setOnShakeListener(object : OnShakeListener {
            override fun onShake(VersionCode: String) {
                Toast.makeText(this@BaseActivity, "Version Code : "+ BuildConfig.VERSION_CODE.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
       mSensorManager?.registerListener(
            mShakeDetector,
            mAccelerometer,
            SensorManager.SENSOR_DELAY_UI
        )

    }


    override fun onPause() { // Add the following line to unregister the Sensor Manager onPause
        mSensorManager?.unregisterListener(mShakeDetector)
        super.onPause()
    }
}