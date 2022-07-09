package com.abilitymap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilitymap.databinding.ActivityEmergencyCallBinding

class EmergencyCallActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmergencyCallBinding
    private lateinit var emergencyCallRVAdapter : EmergencyCallRVAdapter
    private lateinit var personInfoDB : PersonInfoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyCallBinding.inflate(layoutInflater)
        personInfoDB = PersonInfoDatabase.getInstance(this)!!
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

        emergencyCallRVAdapter.setMyItemClickListener(object: EmergencyCallRVAdapter.MyItemClickListener{
            override fun onRemovePerson(PersonId: Int) {

                Log.d("DB", personInfoDB.personInfoDao().getPerson().toString())

                personInfoDB.personInfoDao().deletePerson(PersonId)

            }

        })
        addNewsToDB()
        Log.d("DB", personInfoDB.personInfoDao().getPerson().toString())
    }

    private fun addNewsToDB(){

//        var info = personInfoDB.personInfoDao().getPerson()
//        if (info.isNotEmpty())
//            return
//        personInfoDB.personInfoDao().insertPerson(PersonInfo(info.size,"우리 엄마","010 1234 5678"))
//        personInfoDB.personInfoDao().insertPerson(PersonInfo(info.size,"우리 아빠","010 1234 5678"))
//        personInfoDB.personInfoDao().insertPerson(PersonInfo(info.size,"우리 삼촌","010 1234 5678"))
//        emergencyCallRVAdapter.addPersonInfo(info)
        var info = ArrayList<PersonInfo>()
        info.add(PersonInfo(info.size,"우리 엄마","010 1234 5678"))
        info.add(PersonInfo(info.size,"우리 아빠","010 1234 5678"))
        info.add(PersonInfo(info.size,"우리 삼촌","010 1234 5678"))
        emergencyCallRVAdapter.addPersonInfo(info)
    }

}