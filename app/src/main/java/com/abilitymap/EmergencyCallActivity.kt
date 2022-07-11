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
            override fun onItemClicked(personInfo: PersonInfo, position : Int) {
                val intent = Intent(this@EmergencyCallActivity, AddPhoneBookActivity::class.java)
                intent.putExtra("name", personInfo.name)
                intent.putExtra("phoneNumber", personInfo.phoneNumber)
                intent.putExtra("position", position)
                startActivityForResult(intent, 1000)
            }

        })
        initPersonInfoDB()
    }

    private fun initPersonInfoDB(){     //DB 내의 데이터로 연락처 동기화
        emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)
        Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
    }

    override fun onResume(){    //신규 연락처 저장 후 새로운 연락처로 업데이트
        super.onResume()
        initPersonInfoDB()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            var name = data?.getStringExtra("name")
            var phoneNumber = data?.getStringExtra("phoneNumber")
            var position = data?.getIntExtra("position", 0)
            Log.d("Data from edit text", "데이터 가져오기 성공")
        }
    }

}