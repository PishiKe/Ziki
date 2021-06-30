package com.pishi.ziki.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pishi.ziki.R
import com.pishi.ziki.databinding.ActivityMaximizedPlayerBinding

class MaximizedPlayer : AppCompatActivity() {

    private lateinit var binding: ActivityMaximizedPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maximized_player)

        binding = ActivityMaximizedPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}