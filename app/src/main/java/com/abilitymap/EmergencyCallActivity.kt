package com.abilitymap

import android.app.Activity
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
    lateinit var name : String
    lateinit var phoneNumber : String
    var position : Int = -1

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
        val activity = AddPhoneBookActivity()
        emergencyCallRVAdapter = EmergencyCallRVAdapter()
        emergencyCallRVAdapter.mContext = activity
        binding.rvEmergencyCall.adapter = emergencyCallRVAdapter
        binding.rvEmergencyCall.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        emergencyCallRVAdapter.setMyItemClickListener(object: EmergencyCallRVAdapter.MyItemClickListener{
            override fun onRemovePerson(PersonId: Int) {
                personInfoDB.personInfoDao().deletePerson(PersonId)
                Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
            }
            override fun onItemClicked(personInfo: PersonInfo, position : Int) {
                Log.d("1", "error")
                val intent = Intent(this@EmergencyCallActivity,
                    activity::class.java)     //edit text에 적힌 data 보내기
                intent.putExtra("name", personInfo.name)
                intent.putExtra("phoneNumber", personInfo.phoneNumber)
                intent.putExtra("position", position)
                startActivityForResult(intent, 1000)
                Log.d("2", "error")
            }

            override fun onUpdatePerson(PersonId: Int) {
                emergencyCallRVAdapter.setModifiedData(name, phoneNumber, position)
                personInfoDB.personInfoDao().updatePerson(name, phoneNumber, position)
            }
        })
        initPersonInfoDB()
    }

    private fun initPersonInfoDB(){     //DB 내의 데이터로 연락처 동기화
        emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)
        Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
    }

    override fun onResume(){    //신규 연락처 저장 후 새로운 연락처로 업데이트
        Log.d("4", "error")
        super.onResume()
        initPersonInfoDB()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            name = data?.getStringExtra("name")!!
            phoneNumber = data?.getStringExtra("phoneNumber")!!
            position = data?.getIntExtra("position", 0)
            Log.d("Data from edit text", "데이터 가져오기 성공")

            Log.d("변경 후 pos", position.toString())
            Log.d("변경 후 name", name!!)
            Log.d("변경 후 phoneNumber",phoneNumber!!)

            personInfoDB.personInfoDao().updatePerson(name, phoneNumber, position!!)
            Log.d("DB 수정 후", personInfoDB.personInfoDao().getPersonList().toString())
            emergencyCallRVAdapter.updatePerson(name!!, phoneNumber!!, position!!)
            Log.d("DB 수정 후1", personInfoDB.personInfoDao().getPersonList().toString())
            emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)
            Log.d("DB 수정 후3", personInfoDB.personInfoDao().getPersonList().toString())
        }
    }

}