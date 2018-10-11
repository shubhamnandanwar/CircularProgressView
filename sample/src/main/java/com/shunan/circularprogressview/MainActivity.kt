package com.shunan.circularprogressview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CompoundButton
import android.widget.SeekBar
import com.shunan.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val DEFAULT_MIN_STROKE_WIDTH = 2
    private val DEFAULT_MAX_STROKE_WIDTH = 64

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.progress = 60
        progressBar.animationDuration = 2000
        progressBar.startAngle = 0

        progressSeekBar.max = 100
        progressSeekBar.progress = progressBar.progress
        progressText.text = progressBar.progress.toString()

        progressStrokeWidthSeekBar.max = DEFAULT_MAX_STROKE_WIDTH - DEFAULT_MIN_STROKE_WIDTH
        progressStrokeWidthSeekBar.progress = progressBar.progressWidth.toInt()
        progressStrokeWidthText.text = ("${progressBar.progressWidth.toInt()}dp").toString()

        backgroundStrokeWidthSeekBar.max = DEFAULT_MAX_STROKE_WIDTH - DEFAULT_MIN_STROKE_WIDTH
        backgroundStrokeWidthSeekBar.progress = progressBar.progressBackgroundWidth.toInt()
        backgroundStrokeWidthText.text = ("${progressBar.progressBackgroundWidth.toInt()}dp").toString()

        roundRadio.isChecked = true
        roundRadio.setOnCheckedChangeListener({ _: CompoundButton, b: Boolean ->
            roundRadio.isChecked = b
            flatRadio.isChecked = !b
            progressBar.progressCap = if (b) CircularProgressBar.ROUND else CircularProgressBar.FLAT
        })

        flatRadio.setOnCheckedChangeListener({ _, b ->
            roundRadio.isChecked = !b
            flatRadio.isChecked = b
            progressBar.progressCap = if (b) CircularProgressBar.FLAT else CircularProgressBar.ROUND
        })

        dashEffectCheckBox.setOnCheckedChangeListener({_,b ->
            progressBar.enableBackgroundDashEffect = b
        })

        showDotCheckBox.setOnCheckedChangeListener({ _, b ->
            progressBar.showDot = b
        })

        backgroundStrokeWidthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progressBar.progressBackgroundWidth = p1 + DEFAULT_MIN_STROKE_WIDTH.toFloat()
                backgroundStrokeWidthText.text = ("${progressBar.progressBackgroundWidth.toInt()}dp").toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        progressStrokeWidthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progressBar.progressWidth = p1 + DEFAULT_MIN_STROKE_WIDTH.toFloat()
                progressStrokeWidthText.text = ("${progressBar.progressWidth.toInt()}dp").toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        progressSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                progressBar.progress = i
                progressText.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


    }
}