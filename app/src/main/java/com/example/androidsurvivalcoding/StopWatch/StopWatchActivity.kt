package com.example.androidsurvivalcoding.StopWatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.androidsurvivalcoding.R
import kotlinx.android.synthetic.main.activity_stop_watch.*
import java.util.*
import kotlin.concurrent.timer

class StopWatchActivity : AppCompatActivity() {

    private var time = 0
    private var isRunning = false
    private var timeTask: Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)

        lapButton.setOnClickListener {
            recordLapTime()
        }

        fab.setOnClickListener {

            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        resetFab.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        fab.setImageResource(R.drawable.ic_pause_black_24dp)
        timeTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timeTask?.cancel()
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAB : ${lapTime / 100}.${lapTime % 100}"
        // 맨 위에 랩타임 추가
        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset() {
        timeTask?.cancel()

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        lapLayout.removeAllViews()
        lap = 1
    }
}
