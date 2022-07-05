package com.abilitymap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    lateinit var binding : ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()


    }

    private fun initClickListener(){
        binding.ivNotGoBack.setOnClickListener { finish() }
    }



}