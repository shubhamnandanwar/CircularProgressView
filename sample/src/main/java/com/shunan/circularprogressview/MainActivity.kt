package com.shunan.circularprogressview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //progressBar.progress = -150
        //progressBar.animationDuration = 2000
        //progressBar.startAngle = 0

        seekBar.max = 100
        //seekBar.progress = progressBar.progress

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                //progressBar.progress = i
                //progressBar.refresh()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        /*fadeButton.setOnClickListener({
            progressBar.animationDuration = 500
            progressBar.interpolator = CircularProgressBar.LINEAR
            progressBar.animationEffect = CircularProgressBar.FADE
            progressBar.animateView()
        })

        sweepButton.setOnClickListener({
            progressBar.interpolator = CircularProgressBar.ACCELERATE
            progressBar.animationEffect = CircularProgressBar.SWEEP
            progressBar.animateView()
        })*/
    }
}