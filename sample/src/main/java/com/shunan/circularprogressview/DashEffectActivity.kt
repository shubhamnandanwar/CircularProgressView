package com.shunan.circularprogressview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_dash_effect.*

class DashEffectActivity : AppCompatActivity() {

    val MAX_DASH_LENGTH = 64
    val MIN_DASH_LENGTH = 4
    val DASH_LENGTH_MULTIPLY_FACTOR = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_effect)

        progressBar.progress = 40
        progressBar.enableBackgroundDashEffect = true
        enableDashCheck.isChecked = true

        dashLengthSeekBar.max = (MAX_DASH_LENGTH - MIN_DASH_LENGTH) / DASH_LENGTH_MULTIPLY_FACTOR
        dashLengthSeekBar.progress = progressBar.dashLineLength.toInt() / DASH_LENGTH_MULTIPLY_FACTOR
        dashLengthText.text = progressBar.dashLineLength.toInt().toString()

        dashOffsetSeekBar.max = (MAX_DASH_LENGTH - MIN_DASH_LENGTH) / DASH_LENGTH_MULTIPLY_FACTOR
        dashOffsetSeekBar.progress = progressBar.dashLineOffset.toInt() / DASH_LENGTH_MULTIPLY_FACTOR
        dashOffsetText.text = progressBar.dashLineOffset.toInt().toString()

        dashOffsetSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressBar.dashLineOffset = progress.toFloat() * DASH_LENGTH_MULTIPLY_FACTOR + MIN_DASH_LENGTH
                dashOffsetText.text = progressBar.dashLineOffset.toInt().toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        dashLengthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressBar.dashLineLength = progress.toFloat() * DASH_LENGTH_MULTIPLY_FACTOR + MIN_DASH_LENGTH
                dashLengthText.text = progressBar.dashLineLength.toInt().toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        enableDashCheck.setOnCheckedChangeListener { _, isChecked ->
            progressBar.enableBackgroundDashEffect = isChecked
        }


    }
}
