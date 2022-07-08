package com.abilitymap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityAddPhoneBookBinding

class AddPhoneBookActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddPhoneBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPhoneBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener(){
        binding.ivArrowBackAddPhoneBook.setOnClickListener { finish() }
        binding.tvSaveButtonAddPhoneBook.setOnClickListener {

        }
    }

}