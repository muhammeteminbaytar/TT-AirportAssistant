package com.muhammetbaytar.ttairportassistant.shake

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.muhammetbaytar.ttairportassistant.BuildConfig
import kotlin.math.sqrt

class ShakeDetector: SensorEventListener {

    private var mListener: OnShakeListener? = null
    fun setOnShakeListener(listener: OnShakeListener?) {
        mListener = listener
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ){}

    override fun onSensorChanged(event: SensorEvent) {
        if (mListener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH
            // gForce will be close to 1 when there is no movement.
            val gForce: Float = sqrt(gX * gX + gY * gY + gZ * gZ)
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {

                mListener?.onShake("Version Code : "+BuildConfig.VERSION_CODE.toString())
            }
        }
    }

    companion object {

        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f

    }
}
    interface OnShakeListener {
        fun onShake(VersionCode: String)
    }


