package com.shunan.circularprogressview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shunan.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        fadeButton.setOnClickListener({
            progressBar.animationDuration = 750
            progressBar.interpolator = CircularProgressBar.LINEAR
            progressBar.animationEffect = CircularProgressBar.FADE
            progressBar.animateView()
        })

        sweepButton.setOnClickListener({
            progressBar.animationDuration = 1000
            progressBar.interpolator = CircularProgressBar.ACCELERATE
            progressBar.animationEffect = CircularProgressBar.SWEEP
            progressBar.animateView()
        })
    }
}
