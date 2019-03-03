package com.example.androidsurvivalcoding

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class TiltSensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tiltView: TiltView

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //센서 값이 변동시 호출
        // values[0]: x 축 값 : 위로 기울이면 -10~0, 아래로 기울이면 : 0~10
        // values[1]: y 축 값 : 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 : 0~10
        // values[2]: z 축 값 : 미사용

        event?.let {
            Log.d(
                TAG, "onSensorChanged: " +
                        "x : ${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}"
            )

            tiltView.onSensorEvent(event)

        }
    }

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tilt_sensor)
        // 화면 가로 모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // 화면 안꺼지도록 설정
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        tiltView = TiltView(this)
        setContentView(tiltView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
}
