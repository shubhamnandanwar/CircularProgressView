package com.shunan.circularprogressview

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.shunan.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {

    companion object {
        const val DEFAULT_MIN_ANIMATION_DURATION = 1

        private const val DEFAULT_MAX_ANIMATION_DURATION = 10

        private const val DEFAULT_MAX_FADE_REPEAT_COUNT = 5
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        progressBar.progress = 40
        animationDurationSeekBar.max = DEFAULT_MAX_ANIMATION_DURATION - DEFAULT_MIN_ANIMATION_DURATION
        animationDurationText.text = "${progressBar.animationDuration / 1000f} s"
        animationDurationSeekBar.progress = progressBar.animationDuration / 500 - DEFAULT_MIN_ANIMATION_DURATION
        fadeRepeatCountSeekBar.progress = progressBar.fadeRepeatCount
        fadeRepeatCountSeekBar.max = DEFAULT_MAX_FADE_REPEAT_COUNT
        fadeRepeatCountText.text = progressBar.fadeRepeatCount.toString()
        progressLabel.text = progressBar.progress.toString()
        linearRadioButton.isChecked = true

        animationDurationSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressBar.animationDuration = (progress + DEFAULT_MIN_ANIMATION_DURATION) * 500
                animationDurationText.text = "${progressBar.animationDuration / 1000f} s"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        fadeRepeatCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressBar.fadeRepeatCount = progress
                fadeRepeatCountText.text = progressBar.fadeRepeatCount.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        fadeButton.setOnClickListener { progressBar.fade() }

        sweepButton.setOnClickListener { progressBar.sweep() }

        decrementProgressButton.setOnClickListener {
            if (progressBar.progress >= 10) {
                progressBar.progress -= 10
                progressLabel.text = progressBar.progress.toString()
            }
        }

        incrementProgressButton.setOnClickListener {
            if (progressBar.progress < 100) {
                progressBar.progress += 10
                progressLabel.text = progressBar.progress.toString()
            }
        }

        accelerateRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) progressBar.interpolator = CircularProgressBar.ACCELERATE
        }

        decelerateRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) progressBar.interpolator = CircularProgressBar.DECELERATE
        }

        linearRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) progressBar.interpolator = CircularProgressBar.LINEAR
        }
    }

}
