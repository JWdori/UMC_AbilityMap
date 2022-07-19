package com.abilitymap

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener(){
        binding.tvSkipOnboarding.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvSkipOnboarding.setOnClickListener {
            val intent : Intent = Intent(this, ChooseOptionActivity::class.java)
            startActivity(intent)
        }
    }

}