package com.abilitymap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilitymap.databinding.ActivityEmergencyCallBinding

class EmergencyCallActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmergencyCallBinding
    private lateinit var emergencyCallRVAdapter : EmergencyCallRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
        initRecyclerView()

    }


    private fun initClickListener(){
        binding.ivArrowBackEmergencyCall.setOnClickListener {
            finish()
        }
        binding.ivAddEmergencyCall.setOnClickListener {
            val intent = Intent(this, AddPhoneBookActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(){
        emergencyCallRVAdapter = EmergencyCallRVAdapter()
        binding.rvEmergencyCall.adapter = emergencyCallRVAdapter
        binding.rvEmergencyCall.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        var info = ArrayList<PersonInfo>()
        info.add(PersonInfo(0,"우리 엄마","010 1234 5678"))
        info.add(PersonInfo(1,"우리 아빠","010 1234 5678"))
        emergencyCallRVAdapter.addPersonInfo(info)
    }

}