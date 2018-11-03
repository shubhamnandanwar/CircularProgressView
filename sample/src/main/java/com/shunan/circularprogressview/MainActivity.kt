package com.shunan.circularprogressview

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Circular Progress Bar"

        basicCircularBar.disableDefaultSweep = true
        dashEffectCircularBar.disableDefaultSweep = true

        basicCircularBarButton.setOnClickListener {
            startActivity(Intent(applicationContext, BasicCircularBarActivity::class.java))
        }

        animationButton.setOnClickListener {
            startActivity(Intent(applicationContext, AnimationActivity::class.java))
        }

        dashEffectButton.setOnClickListener {
            startActivity(Intent(applicationContext, DashEffectActivity::class.java))
        }

    }
}