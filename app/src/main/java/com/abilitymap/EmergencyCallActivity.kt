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
                personInfoDB.personInfoDao().deletePerson(PersonId)
                Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
            }

        })
        insertPersonToDB()
        Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
    }

    private fun insertPersonToDB(){

        var info = personInfoDB.personInfoDao().getPersonList()

        emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)

        if (info.isNotEmpty())
            return
        personInfoDB.personInfoDao().insertPerson(PersonInfo("우리 엄마","010 1234 5678"))

        personInfoDB.personInfoDao().insertPerson(PersonInfo("우리 아빠","010 1234 5678"))

        personInfoDB.personInfoDao().insertPerson(PersonInfo("우리 삼촌","010 1234 5678"))

        Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())

        emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)
    }

}