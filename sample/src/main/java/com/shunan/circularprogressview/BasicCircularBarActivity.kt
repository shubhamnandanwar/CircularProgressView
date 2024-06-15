package com.shunan.circularprogressview

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shunan.circularprogressbar.CircularProgressBar
import com.shunan.circularprogressview.databinding.ActivityBasicCircularBarBinding

class BasicCircularBarActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasicCircularBarBinding

    companion object {
        private const val DEFAULT_MIN_STROKE_WIDTH = 2
        private const val DEFAULT_MAX_STROKE_WIDTH = 64

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_circular_bar)

        binding.showDotCheckBox.isChecked = true
        binding.progressBar.progress = 60
        binding.progressBar.startAngle = 0
        binding.progressSeekBar.max = 100
        binding.progressSeekBar.progress = binding.progressBar.progress
        binding.progressText.text = binding.progressBar.progress.toString()
        binding.progressStrokeWidthSeekBar.max = DEFAULT_MAX_STROKE_WIDTH - DEFAULT_MIN_STROKE_WIDTH
        binding.progressStrokeWidthSeekBar.progress = binding.progressBar.progressWidth.toInt()
        binding.progressStrokeWidthText.text = ("${binding.progressBar.progressWidth.toInt()}dp").toString()
        binding.backgroundStrokeWidthSeekBar.max = DEFAULT_MAX_STROKE_WIDTH - DEFAULT_MIN_STROKE_WIDTH
        binding.backgroundStrokeWidthSeekBar.progress = binding.progressBar.progressBackgroundWidth.toInt()
        binding.backgroundStrokeWidthText.text = ("${binding.progressBar.progressBackgroundWidth.toInt()}dp").toString()
        binding.roundRadio.isChecked = true
        binding.roundRadio.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            binding.roundRadio.isChecked = b
            binding.flatRadio.isChecked = !b
            binding.progressBar.progressCap = if (b) CircularProgressBar.ROUND else CircularProgressBar.FLAT
        }

        binding.flatRadio.setOnCheckedChangeListener { _, b ->
            binding.roundRadio.isChecked = !b
            binding.flatRadio.isChecked = b
            binding.progressBar.progressCap = if (b) CircularProgressBar.FLAT else CircularProgressBar.ROUND
        }

        binding.showDotCheckBox.setOnCheckedChangeListener { _, b ->
            binding.progressBar.showDot = b
        }

        binding.backgroundStrokeWidthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.progressBar.progressBackgroundWidth = p1 + DEFAULT_MIN_STROKE_WIDTH.toFloat()
                binding.backgroundStrokeWidthText.text = ("${binding.progressBar.progressBackgroundWidth.toInt()}dp").toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        binding.progressStrokeWidthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.progressBar.progressWidth = p1 + DEFAULT_MIN_STROKE_WIDTH.toFloat()
                binding.progressStrokeWidthText.text = ("${binding.progressBar.progressWidth.toInt()}dp").toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        binding.progressSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                binding.progressBar.progress = i
                binding.progressText.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

    }
}
