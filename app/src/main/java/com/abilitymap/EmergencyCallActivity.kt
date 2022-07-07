package com.abilitymap

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilitymap.databinding.ActivityEmergencyCallBinding

class EmergencyCallActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmergencyCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

    }


    private fun initRecyclerView(){
        val emergencyCallRVAdapter = EmergencyCallRVAdapter()
        binding.rvEmergencyCall.adapter = emergencyCallRVAdapter
        binding.rvEmergencyCall.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        var info = ArrayList<PersonInfo>()
        var person1 = PersonInfo()
        var person2 = PersonInfo()
        person1.name = "우리 엄마"
        person1.phoneNumber = "010 1234 5678"
        person2.name = "우리 아빠"
        person2.phoneNumber = "010 1234 5678"
        emergencyCallRVAdapter.addPersonInfo(info)
    }

}