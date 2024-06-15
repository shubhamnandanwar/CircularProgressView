package com.shunan.circularprogressview

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shunan.circularprogressview.databinding.ActivityDashEffectBinding

class DashEffectActivity : AppCompatActivity() {


    lateinit var binding: ActivityDashEffectBinding

    companion object {

        private const val MAX_DASH_LENGTH = 64
        const val MIN_DASH_LENGTH = 4
        const val DASH_LENGTH_MULTIPLY_FACTOR = 4

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_effect)

        binding.progressBar.progress = 40
        binding.progressBar.enableBackgroundDashEffect = true
        binding.enableDashCheck.isChecked = true
        binding.dashLengthSeekBar.max = (MAX_DASH_LENGTH - MIN_DASH_LENGTH) / DASH_LENGTH_MULTIPLY_FACTOR
        binding.dashLengthSeekBar.progress = binding.progressBar.dashLineLength.toInt() / DASH_LENGTH_MULTIPLY_FACTOR
        binding.dashLengthText.text = binding.progressBar.dashLineLength.toInt().toString()
        binding.dashOffsetSeekBar.max = (MAX_DASH_LENGTH - MIN_DASH_LENGTH) / DASH_LENGTH_MULTIPLY_FACTOR
        binding.dashOffsetSeekBar.progress = binding.progressBar.dashLineOffset.toInt() / DASH_LENGTH_MULTIPLY_FACTOR
        binding.dashOffsetText.text = binding.progressBar.dashLineOffset.toInt().toString()
        binding.dashOffsetSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressBar.dashLineOffset = progress.toFloat() * DASH_LENGTH_MULTIPLY_FACTOR + MIN_DASH_LENGTH
                binding.dashOffsetText.text = binding.progressBar.dashLineOffset.toInt().toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        binding.dashLengthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressBar.dashLineLength = progress.toFloat() * DASH_LENGTH_MULTIPLY_FACTOR + MIN_DASH_LENGTH
                binding.dashLengthText.text = binding.progressBar.dashLineLength.toInt().toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        binding.enableDashCheck.setOnCheckedChangeListener { _, isChecked ->
            binding.progressBar.enableBackgroundDashEffect = isChecked
        }

    }
}
