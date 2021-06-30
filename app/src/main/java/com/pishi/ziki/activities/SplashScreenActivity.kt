package com.pishi.ziki.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pishi.ziki.R
import com.pishi.ziki.databinding.ActivityMaximizedPlayerBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaximizedPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



    }
}