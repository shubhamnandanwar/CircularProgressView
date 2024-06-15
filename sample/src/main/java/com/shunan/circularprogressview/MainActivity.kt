package com.shunan.circularprogressview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shunan.circularprogressview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar!!.title = "Circular Progress Bar"

        binding.basicCircularBar.progress = 40
        binding.basicCircularBar.disableDefaultSweep = true
        binding.animatedCircularBar.progress = 60
        binding.dashEffectCircularBar.progress = 40
        binding.dashEffectCircularBar.disableDefaultSweep = true

        binding.basicCircularBarButton.setOnClickListener {
            startActivity(Intent(applicationContext, BasicCircularBarActivity::class.java))
        }

        binding.animationButton.setOnClickListener {
            startActivity(Intent(applicationContext, AnimationActivity::class.java))
        }

        binding.dashEffectButton.setOnClickListener {
            startActivity(Intent(applicationContext, DashEffectActivity::class.java))
        }

    }
}