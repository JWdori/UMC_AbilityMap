package com.abilitymap

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        binding.clAddEmergencyCall.setOnClickListener {
            putSPF("","",-1)

            for (i : Int in 0..emergencyCallRVAdapter.itemCount-1){
                val holder : RecyclerView.ViewHolder = binding.rvEmergencyCall.findViewHolderForAdapterPosition(i)!!
                if (holder.itemView.findViewById<TextView>(R.id.flag).text.toString().equals("true")){         //클릭 된 것을 제외한 view들 원 상태로 복귀
                    resetViewHolder(holder)
                    break
                }
            }

            val intent = Intent(this, AddPhoneBookActivity::class.java)
            startActivity(intent)
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
            override fun onResetViewHolder(holder: EmergencyCallRVAdapter.ViewHolder, position : Int) {
                resetViewHolder(holder)
                putSPF("","", -1)
            }

            override fun onRemovePerson(PersonId: Int) {
                personInfoDB.personInfoDao().deletePerson(PersonId)
                Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
                checkNumberOfItems()
                binding.tvNumOfInfoEmergencyCall.setText(personInfoDB.personInfoDao().getPersonList().size.toString()+"/5")
            }
            override fun onItemClicked(personInfo: PersonInfo, position : Int, name : String, phoneNumber : String, bindingE: ItemEmergencyCallBinding) {

                if(bindingE.flag.text.toString().equals("true")){  //클릭된 layout이 직전 클릭된 layout과 동일할 시
                    resetViewHolder(binding.rvEmergencyCall.findViewHolderForAdapterPosition(position)!!)
                    bindingE.flag.setText("false")
                    putSPF("", "", -1)
                }
                else{
                    //클릭된 layout 배경 변경
                    bindingE.layoutEmergencyCall.setBackgroundDrawable(resources.getDrawable(R.drawable.rectangle_clicked))
                    bindingE.tvNameEmergencyCall.setTextColor(Color.parseColor("#ffffff"))
                    bindingE.tvPhoneNumberEmergencyCall.setTextColor(Color.parseColor("#ffffff"))
                    bindingE.ivDeleteEmergencyCall.visibility = View.INVISIBLE
                    bindingE.ivDeleteEmergencyCallWhite.visibility = View.VISIBLE
                    bindingE.flag.setText("true")

                    //직전 클릭이 rv의 layout일 시 직전 layout 배경 reset

                    for (i : Int in 0..emergencyCallRVAdapter.itemCount-1){

                        val holder : RecyclerView.ViewHolder = binding.rvEmergencyCall.findViewHolderForAdapterPosition(i)!!

                        if (i != position && holder.itemView.findViewById<TextView>(R.id.flag).text.toString().equals("true")){         //클릭 된 것을 제외한 view들 원 상태로 복귀
                            resetViewHolder(holder)
                            break
                        }
                    }
                    putSPF(name, phoneNumber, position)
                }



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
        checkSelectedPersonInfo()
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

    private fun putSPF(name : String, phoneNumber : String, position : Int){
        spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = spfPersonInfo.edit()
        editor.putString("name", name)
        editor.putString("phoneNumber", phoneNumber)
        editor.putInt("position", position)
        editor.apply()
        editor.commit()
    }

    private fun resetViewHolder(holder : RecyclerView.ViewHolder){
        holder.itemView.findViewById<ConstraintLayout>(R.id.layout_emergency_call).setBackgroundDrawable(resources.getDrawable(R.drawable.rectangle))
        holder.itemView.findViewById<TextView>(R.id.tv_name_emergency_call).setTextColor(Color.parseColor("#000000"))
        holder.itemView.findViewById<TextView>(R.id.tv_phoneNumber_emergency_call).setTextColor(Color.parseColor("#000000"))
        holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call).visibility = View.VISIBLE
        holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call_white).visibility = View.INVISIBLE
        holder.itemView.findViewById<TextView>(R.id.flag).setText("false")
    }

    private fun checkSelectedPersonInfo(){
        spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
        val pos : Int = spfPersonInfo.getInt("position", -1)

        if(pos!=-1){
            binding.rvEmergencyCall.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    val holder : RecyclerView.ViewHolder = binding.rvEmergencyCall.findViewHolderForAdapterPosition(pos)!!

                    holder.itemView.findViewById<ConstraintLayout>(R.id.layout_emergency_call).setBackgroundDrawable(resources.getDrawable(R.drawable.rectangle_clicked))
                    holder.itemView.findViewById<TextView>(R.id.tv_name_emergency_call).setTextColor(Color.parseColor("#ffffff"))
                    holder.itemView.findViewById<TextView>(R.id.tv_phoneNumber_emergency_call).setTextColor(Color.parseColor("#ffffff"))
                    holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call).visibility = View.INVISIBLE
                    holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call_white).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.flag).setText("true")

                    binding.rvEmergencyCall.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    }
            })
        }

    }

}