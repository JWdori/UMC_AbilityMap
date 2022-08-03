package com.abilitymap.ui.oss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityOssBinding

class OssActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOssBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOssBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener(){
        binding.ivArrowOss.setOnClickListener { finish() }
    }

}