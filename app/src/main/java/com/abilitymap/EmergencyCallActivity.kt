package com.abilitymap

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilitymap.databinding.ActivityEmergencyCallBinding
import com.abilitymap.databinding.ItemEmergencyCallBinding

class EmergencyCallActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmergencyCallBinding
    private lateinit var emergencyCallRVAdapter : EmergencyCallRVAdapter
    private lateinit var personInfoDB : PersonInfoDatabase
    private lateinit var spfPersonInfo : SharedPreferences
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
        binding.clAddClickEmergencyCall.setOnClickListener {
            val intent = Intent(this, AddPhoneBookActivity::class.java)
            startActivity(intent)
        }
        binding.cl119EmergencyCall.setOnClickListener {

            spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
            val editor : SharedPreferences.Editor = spfPersonInfo.edit()
            editor.putString("name", "119")
            editor.putString("phoneNumber", "119")
            editor.apply()
            editor.commit()

        }
    }

    private fun setUpRecyclerView(){
        emergencyCallRVAdapter = EmergencyCallRVAdapter()
        emergencyCallRVAdapter.mContext = this
        binding.rvEmergencyCall.adapter = emergencyCallRVAdapter
        binding.rvEmergencyCall.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun initRecyclerView(){
        setUpRecyclerView()
        emergencyCallRVAdapter.setMyItemClickListener(object: EmergencyCallRVAdapter.MyItemClickListener{
            override fun onRemovePerson(PersonId: Int) {
                personInfoDB.personInfoDao().deletePerson(PersonId)
                Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
                checkNumberOfItems()
                binding.tvNumOfInfoEmergencyCall.setText(personInfoDB.personInfoDao().getPersonList().size.toString()+"/5")
            }
            override fun onItemClicked(personInfo: PersonInfo, position : Int, name : String, phoneNumber : String, binding: ItemEmergencyCallBinding) {

                binding.layoutEmergencyCall.setBackgroundDrawable(resources.getDrawable(R.drawable.rectangle_clicked))
                binding.flag.setText("true")

                for (i : Int in 1..emergencyCallRVAdapter.itemCount){
                    if (i != position && binding.flag.text.toString().equals("true")){         //클릭 된 것을 제외한 view들 원 상태로 복귀

                    }
                }
                putSPF(name, phoneNumber)

//                val intent = Intent(this@EmergencyCallActivity,
//                    activity::class.java)     //edit text에 적힌 data 보내기
//                intent.putExtra("name", personInfo.name)
//                intent.putExtra("phoneNumber", personInfo.phoneNumber)
//                intent.putExtra("position", position)
//                startActivityForResult(intent, 1000)
            }

//            override fun onUpdatePerson(PersonId: Int) {
//                emergencyCallRVAdapter.setModifiedData(name, phoneNumber, position)
//                personInfoDB.personInfoDao().updatePerson(name, phoneNumber, position)
//            }
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
        checkNumberOfItems()
        binding.tvNumOfInfoEmergencyCall.setText(personInfoDB.personInfoDao().getPersonList().size.toString()+"/5")
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == RESULT_OK){
//            name = data?.getStringExtra("name")!!
//            phoneNumber = data?.getStringExtra("phoneNumber")!!
//            position = data?.getIntExtra("position", 0)
//            Log.d("Data from edit text", "데이터 가져오기 성공")
//
//            Log.d("변경 후 pos", position.toString())
//            Log.d("변경 후 name", name!!)
//            Log.d("변경 후 phoneNumber",phoneNumber!!)
//
//            personInfoDB.personInfoDao().updatePerson(name, phoneNumber, position!!)
//            Log.d("DB 수정 후", personInfoDB.personInfoDao().getPersonList().toString())
//            emergencyCallRVAdapter.updatePerson(name!!, phoneNumber!!, position!!)
//            Log.d("DB 수정 후1", personInfoDB.personInfoDao().getPersonList().toString())
//            emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)
//            Log.d("DB 수정 후3", personInfoDB.personInfoDao().getPersonList().toString())
//        }
//    }

    private fun checkNumberOfItems(){
        if (personInfoDB.personInfoDao().getPersonList().size>=5)
            binding.clAddEmergencyCall.visibility = View.GONE
        else
            binding.clAddEmergencyCall.visibility = View.VISIBLE
    }

    private fun putSPF(name : String, phoneNumber : String){
        spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = spfPersonInfo.edit()
        editor.putString("name", name)
        editor.putString("phoneNumber", phoneNumber)
        editor.apply()
        editor.commit()
    }

}