package com.shunan.circularprogressview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.shunan.circularprogressbar.CircularProgressBar
import com.shunan.circularprogressview.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationBinding

    companion object {
        const val DEFAULT_MIN_ANIMATION_DURATION = 1

        private const val DEFAULT_MAX_ANIMATION_DURATION = 10

        private const val DEFAULT_MAX_FADE_REPEAT_COUNT = 5
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animation)
        binding.progressBar.progress = 40
        binding.animationDurationSeekBar.max = DEFAULT_MAX_ANIMATION_DURATION - DEFAULT_MIN_ANIMATION_DURATION
        binding.animationDurationText.text = "${binding.progressBar.animationDuration / 1000f} s"
        binding.animationDurationSeekBar.progress = binding.progressBar.animationDuration / 500 - DEFAULT_MIN_ANIMATION_DURATION
        binding.fadeRepeatCountSeekBar.progress = binding.progressBar.fadeRepeatCount
        binding.fadeRepeatCountSeekBar.max = DEFAULT_MAX_FADE_REPEAT_COUNT
        binding.fadeRepeatCountText.text = binding.progressBar.fadeRepeatCount.toString()
        binding.progressLabel.text = binding.progressBar.progress.toString()
        binding.linearRadioButton.isChecked = true

        binding.animationDurationSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressBar.animationDuration = (progress + DEFAULT_MIN_ANIMATION_DURATION) * 500
                binding.animationDurationText.text = "${binding.progressBar.animationDuration / 1000f} s"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        binding.fadeRepeatCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressBar.fadeRepeatCount = progress
                binding.fadeRepeatCountText.text = binding.progressBar.fadeRepeatCount.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.fadeButton.setOnClickListener { binding.progressBar.fade() }

        binding.sweepButton.setOnClickListener { binding.progressBar.sweep() }

        binding.decrementProgressButton.setOnClickListener {
            if (binding.progressBar.progress >= 10) {
                binding.progressBar.progress -= 10
                binding.progressLabel.text = binding.progressBar.progress.toString()
            }
        }

        binding.incrementProgressButton.setOnClickListener {
            if (binding.progressBar.progress < 100) {
                binding.progressBar.progress += 10
                binding.progressLabel.text = binding.progressBar.progress.toString()
            }
        }

        binding.accelerateRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.progressBar.interpolator = CircularProgressBar.ACCELERATE
        }

        binding.decelerateRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.progressBar.interpolator = CircularProgressBar.DECELERATE
        }

        binding.linearRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.progressBar.interpolator = CircularProgressBar.LINEAR
        }
    }

}
