package com.abilitymap

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abilitymap.databinding.ActivityEmergencyCallBinding

class EmergencyCallActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmergencyCallBinding
    private lateinit var emergencyCallRVAdapter : EmergencyCallRVAdapter
    private lateinit var personInfoDB : PersonInfoDatabase
    private lateinit var spfPersonInfo : SharedPreferences
    lateinit var name : String
    lateinit var phoneNumber : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyCallBinding.inflate(layoutInflater)
        personInfoDB = PersonInfoDatabase.getInstance(this)!!
        setContentView(binding.root)

        initClickListener()
        initRecyclerView()
        checkSelectedPersonInfo()
    }

    private fun initClickListener(){
        binding.ivArrowBackEmergencyCall.setOnClickListener {   //뒤로가기 클릭 시 activity 종료
            finish()
        }
        binding.clAddEmergencyCall.setOnClickListener {     //연락처 추가 layout 클릭 시 AddPhoneBookActivity로 전환

            val intent = Intent(this, AddPhoneBookActivity::class.java)
            startActivity(intent)
        }

        binding.rvEmergencyCall.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //recyclerview의 scroll 발생 시 마다 동작
            //새로 선택된 연락처를 찾아 선택 표시 on (스크롤 감지 코드 없을 시 화면 밖의 선택 연락처가 표시되지 않던 오류 해결하기 위함, flag true 값을 못 찾아냈었음)
            //이전 연락처는 선택 표시 off

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val spf : SharedPreferences = getSharedPreferences("personInfo", MODE_PRIVATE)
                val position : Int = spf.getInt("position", -1)

                if (position != -1){
                    for (i : Int in 0 until emergencyCallRVAdapter.itemCount){  //모든 viewHolder iterate
                        try{
                            val holder : RecyclerView.ViewHolder = binding.rvEmergencyCall.findViewHolderForAdapterPosition(i)!!
                            // 이전 연락처 선택 표시 off
                            if (i != position && holder.itemView.findViewById<TextView>(R.id.flag).text.toString().equals("true")){   //클릭 된 것을 제외한 view들 원 상태로 복귀
                                resetViewHolder(holder, resources.getDrawable(R.drawable.rectangle), "#000000", true)
                            }
                            // 새롭게 선택된 연락처 선택 표시 on
                            else if(i == position){
                                resetViewHolder(holder, resources.getDrawable(R.drawable.rectangle_clicked), "#ffffff", false)
                            }
                        }
                        catch(e : NullPointerException){
                        }   //첫 실행 시 ViewHolderAdapter의 NullPointerException 발생 pass 하기 위함
                    }
                }

            }
        })

    }

    private fun setUpRecyclerView(){    //준비해둔 RVAdapter와 recyclerview를 연결
        emergencyCallRVAdapter = EmergencyCallRVAdapter()
        emergencyCallRVAdapter.mContext = this
        emergencyCallRVAdapter.recyclerView = binding.rvEmergencyCall
        binding.rvEmergencyCall.adapter = emergencyCallRVAdapter
        binding.rvEmergencyCall.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun initRecyclerView(){

        setUpRecyclerView()

        emergencyCallRVAdapter.setMyItemClickListener(object: EmergencyCallRVAdapter.MyItemClickListener{
            //RVAdapter 쪽에서의 클릭 리스너 관련 데이터 처리하기 위한 call back interface
            //Caller로서 Callee인 RVAdapter가 동작 도중 event가 발생하면 해당 interface를 통해 Caller를 호출하여 이로하여금 업무 처리
            //필요한 연산 수행을 위한 listener 등록

            //RVAdapter에서 삭제 아이콘 클릭 시 대상이 선택 연락처인지 판별 후 그에 맞는 대응
            override fun onResetViewHolder(holder: EmergencyCallRVAdapter.ViewHolder, position : Int, size : Int) {

                //삭제 대상이 선택된 연락처일 시 선택 표시 off
                if (holder.itemView.findViewById<TextView>(R.id.flag).text.toString().equals("true")) {
                    putSPF(-1, -1)
                    resetViewHolder(holder, resources.getDrawable(R.drawable.rectangle), "#000000", true)
                }
                //삭제 대상이 선택된 연락처가 아닌 다른 연락처일 시
                else{
                    val spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
                    val personId = spfPersonInfo.getInt("personId", -1)
                    val pos = spfPersonInfo.getInt("position", -1)

                    //선택된 연락처 viewholder의 위에 위치한 viewholder가 삭제될 시
                    //선택 표시된 연락처 layout이 위로 한 칸 옮겨지도록
                    if (pos > position){    //pos : 선택 연락처 index , position : 삭제 아이콘 눌린 연락처 index
                        putSPF(personId,pos-1)
                        checkSelectedPersonInfo()
                    }
                    //아래 위치한 viewholder가 삭제될 시 해당 대상 지워줌
                    else if(pos < position){
                        checkSelectedPersonInfo()
                    }
                }


            }

            override fun onRemovePerson(PersonId: Int) {    //ViewHolder 하나가 삭제될 시 DB 갱신 및 잔여 연락처 개수 동기화
                personInfoDB.personInfoDao().deletePerson(PersonId)
                Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
                checkNumberOfItems()
                binding.tvNumOfInfoEmergencyCall.setText(personInfoDB.personInfoDao().getPersonList().size.toString()+"/5")
            }
            override fun onItemClicked(personId : Int, position : Int) {
                //연락처 layout이 클릭될 시 sharedPreference에 해당 연락처 정보 삽입
                // -> 해당 정보를 이용해 어떤 adapter position의 viewholder를 UI상에 표시할 지 결정
                putSPF(personId, position)

            }

            override fun onUpdatePerson(personId: Int, personInfo : PersonInfo, position : Int) {
                //RVAdapter의 수정 icon 클릭 시 AddPhoneBookActivity로의 전환 수행은
                //해당 class에서 하는 것보다 본 Activity에서 하는 것이 자연스러움

                val intent = Intent(this@EmergencyCallActivity, AddPhoneBookActivity::class.java)     //edit text에 적힌 data 보내기
                intent.putExtra("name", personInfo.name)
                intent.putExtra("phoneNumber", personInfo.phoneNumber)
                intent.putExtra("text",personInfo.text)
                intent.putExtra("personId", personId)
                intent.putExtra("position", position)
                startActivityForResult(intent, 1000)
            }
        })

        initPersonInfoDB()  //DB 내의 데이터로 RVAdapter 쪽의 연락처 동기화
    }

    private fun initPersonInfoDB(){
        //DB 내의 데이터로 RVAdapter 쪽의 연락처 동기화
        //ViewHolder의 Binding이 DB의 정보를 바탕으로 이루어지고 있기 때문에 DB에 변경 사항 있을 시 즉각 반영하기 위함
        try{
            emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)
        }catch(e : Exception){
            Toast.makeText(this,"재실행 시도 후 오류 재발생 시 앱 데이터를 삭제해 주세요", Toast.LENGTH_SHORT).show()
        }
        Log.d("DB", personInfoDB.personInfoDao().getPersonList().toString())
    }

    override fun onResume(){    //신규 연락처 저장 및 기존 연락처 삭제 이후 변경사항 UI에 반영
        super.onResume()
        initPersonInfoDB()
        checkNumberOfItems()
        binding.tvNumOfInfoEmergencyCall.setText(personInfoDB.personInfoDao().getPersonList().size.toString()+"/5")
        checkSelectedPersonInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //수정 icon 클릭 이후 AddPhoneBookActivity에서 변경된 연락처 내용들을 가져와 DB 및 ViewHolder update

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            name = data?.getStringExtra("name")!!
            phoneNumber = data?.getStringExtra("phoneNumber")!!
            val text = data?.getStringExtra("text")!!
            val position = data?.getIntExtra("position", 0)
            val personId = data?.getIntExtra("personId", 0)

            Log.d("Data from edit text", "데이터 가져오기 성공")

            Log.d("변경 후 pos", position.toString())
            Log.d("변경 후 name", name!!)
            Log.d("변경 후 phoneNumber",phoneNumber!!)
            Log.d("변경 후 text",text!!)

            personInfoDB.personInfoDao().updatePerson(name, phoneNumber, text, personId)    //DB 데이터 수정

            emergencyCallRVAdapter.updatePerson(name, phoneNumber, text, position)      //recyclerview의 viewholder 정보 갱신

            emergencyCallRVAdapter.addPersonInfo(personInfoDB.personInfoDao().getPersonList() as ArrayList<PersonInfo>)     //RVAdapter의 데이터 수정

            val spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
            val spfPosition : Int = spfPersonInfo.getInt("position", -1)
            if(spfPosition == position){
                //수정 icon이 선택되었던 연락처의 것이었을 시 곧바로 변경된 data로 동기화
                // (수정내용 저장 이후 곧바로 mainActivity로 나가 text보낼 때 변경된 내용을 바탕으로 작동하기 위함)
                putSPF(personId, position)
            }

            Log.d("DB 수정 후1", personInfoDB.personInfoDao().getPersonList().toString())
        }
    }

    private fun checkNumberOfItems(){   //연락처 개수 5개로 한정하기 위해 5개 충족 시 추가 버튼 안보이게끔
        if (personInfoDB.personInfoDao().getPersonList().size>=5)
            binding.clAddEmergencyCall.visibility = View.GONE
        else
            binding.clAddEmergencyCall.visibility = View.VISIBLE
    }

    private fun putSPF(personId : Int, position : Int){
        //선택되었던 연락처의 정보를 내부에 저장하여 앱을 껐다 켜도 해당 내용을 간직하고 반영함
        //연락처의 ID 및 ViewHolder의 position값을 바탕으로 위의 작업 수행
        spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = spfPersonInfo.edit()
        editor.putInt("personId", personId)
        editor.putInt("position", position)
        editor.apply()
        editor.commit()
    }

    private fun resetViewHolder(holder : RecyclerView.ViewHolder, img : Drawable, color : String, isCliked : Boolean){
        //선택 연락처 표시 On or Off
        //by setting background with different image, icon and text color
        holder.itemView.findViewById<ConstraintLayout>(R.id.layout_emergency_call).setBackgroundDrawable(img)
        holder.itemView.findViewById<TextView>(R.id.tv_name_emergency_call).setTextColor(Color.parseColor(color))
        holder.itemView.findViewById<TextView>(R.id.tv_phoneNumber_emergency_call).setTextColor(Color.parseColor(color))

        if (isCliked){
            holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call).visibility = View.VISIBLE
            holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call_white).visibility = View.INVISIBLE
            holder.itemView.findViewById<ImageView>(R.id.iv_update_emergency_call).visibility = View.VISIBLE
            holder.itemView.findViewById<ImageView>(R.id.iv_update_emergency_call_white).visibility = View.INVISIBLE
            holder.itemView.findViewById<TextView>(R.id.flag).setText("false")
        }
        else{
            holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call).visibility = View.INVISIBLE
            holder.itemView.findViewById<ImageView>(R.id.iv_delete_emergency_call_white).visibility = View.VISIBLE
            holder.itemView.findViewById<ImageView>(R.id.iv_update_emergency_call).visibility = View.INVISIBLE
            holder.itemView.findViewById<ImageView>(R.id.iv_update_emergency_call_white).visibility = View.VISIBLE
            holder.itemView.findViewById<TextView>(R.id.flag).setText("true")
        }

    }

    private fun checkSelectedPersonInfo(){
        //본 Activity로 화면이 재전환 될 때 연락처 선택 표시를 재개하기 위함

        //recyclerview에 listener를 등록시켜
        //전체 뷰가 그려질 때, global layout 상태 및 visibility 여부가 변경될 때
        //sharedPreference에 저장해둔 선택 연락처의 position값으로
        //ViewHolder의 선택 표시 On 및 이전 연락처 선택 표시 Off
        //이와 같이 작업하는 이유는 findViewHolderForAdapterPosition이 Null값을 반환하는 경우가 있기 때문
        //(ViewHolder가 제대로 load 되기 전에 실행되어 발생하는 오류)

        spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE)
        val personId : Int = spfPersonInfo.getInt("personId", -1)
        val pos : Int = spfPersonInfo.getInt("position", -1)

        Log.d("check !!! : ",pos.toString())

        if(personId!=-1){
            binding.rvEmergencyCall.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    try{
                        val holder : RecyclerView.ViewHolder = binding.rvEmergencyCall.findViewHolderForAdapterPosition(pos)!!

                        resetViewHolder(holder, resources.getDrawable(R.drawable.rectangle_clicked), "#ffffff", false)

                        binding.rvEmergencyCall.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    }
                    catch(e : NullPointerException){
                    }
                    for (i : Int in 0 until emergencyCallRVAdapter.itemCount){
                        try{
                            val holder : RecyclerView.ViewHolder = binding.rvEmergencyCall.findViewHolderForAdapterPosition(i)!!

                            if (i != pos && holder.itemView.findViewById<TextView>(R.id.flag).text.toString().equals("true")){   //클릭 된 것을 제외한 view들 원 상태로 복귀
                                resetViewHolder(holder, resources.getDrawable(R.drawable.rectangle), "#000000", true)
                            }
                        }
                        catch(e : NullPointerException){
                        }
                    }

                    }

            })
        }

    }


}