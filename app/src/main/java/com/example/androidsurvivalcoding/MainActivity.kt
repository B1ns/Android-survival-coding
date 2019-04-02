package com.example.androidsurvivalcoding

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.androidsurvivalcoding.Bmi.BmiMainActivity
import com.example.androidsurvivalcoding.GpsMap.MapsActivity
import com.example.androidsurvivalcoding.PhotoFrame.MyGalleryActivity
import com.example.androidsurvivalcoding.StopWatch.StopWatchActivity
import com.example.androidsurvivalcoding.TiltSensor.TiltSensorActivity
import com.example.androidsurvivalcoding.WebBrowser.MyWebBrowserActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object {
        private val FINSH_INTERVAL_TIME = 2000
        private var backPressedTime: Long = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bmi.setOnClickListener {
            startActivity<BmiMainActivity>()
        }

        stopWatch.setOnClickListener {
            startActivity<StopWatchActivity>()
        }

        myWebBrowser.setOnClickListener {
            startActivity<MyWebBrowserActivity>()
        }

        tiltSensor.setOnClickListener {
            startActivity<TiltSensorActivity>()
        }
        myGallery.setOnClickListener {
            startActivity<MyGalleryActivity>()
        }

        gpsMap.setOnClickListener {
            startActivity<MapsActivity>()
        }
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime
        if (intervalTime in 0..FINSH_INTERVAL_TIME) {
            ActivityCompat.finishAffinity(this)
        } else {
            backPressedTime = tempTime
            toast("종료하실건가요?\n뒤로가기버튼을 다시눌러주세요")
        }
    }
}
