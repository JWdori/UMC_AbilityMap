package com.abilitymap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityChooseOptionBinding

class ChooseOptionActivity :AppCompatActivity() {

    private lateinit var binding : ActivityChooseOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}