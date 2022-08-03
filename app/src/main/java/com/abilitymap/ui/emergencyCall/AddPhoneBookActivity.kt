package com.abilitymap.ui.emergencyCall

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.data.personInfo.PersonInfo
import com.abilitymap.data.personInfo.PersonInfoDatabase
import com.abilitymap.R
import com.abilitymap.databinding.ActivityAddPhoneBookBinding

class AddPhoneBookActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddPhoneBookBinding
    lateinit var context : Context
    private lateinit var personInfoDatabase: PersonInfoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPhoneBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        personInfoDatabase = PersonInfoDatabase.getInstance(this)!!
        Log.d("PersonInfoDataBase", personInfoDatabase.personInfoDao().getPersonList().toString())
        initData()
        initClickListener()
        checkButtonEffect()

    }

    private fun initClickListener(){
        binding.ivArrowBackAddPhoneBook.setOnClickListener { finish() } //뒤로가기 버튼 시 종료
        binding.tvSaveButtonAddPhoneBook.setOnClickListener {   //저장 버튼 눌릴 시 관련 연산 처리
            addValidInfoToDB()
        }
        binding.etNameAddPhoneBook.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvNameNumberAddPhoneBook.text = "0/8 자"
                checkButtonEffect()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvNameNumberAddPhoneBook.text = binding.etNameAddPhoneBook.text.toString().length.toString() + "/8 자"
                checkButtonEffect()
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.tvNameNumberAddPhoneBook.text = binding.etNameAddPhoneBook.text.toString().length.toString() + "/8 자"
                checkButtonEffect()
            }
        })
        binding.etPhoneNumberAddPhoneBook.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkButtonEffect()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkButtonEffect()
            }

            override fun afterTextChanged(p0: Editable?) {
                checkButtonEffect()
            }
        })
        binding.etTextAddPhoneBook.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvTextNumberAddPhoneBook.text = "0/30 자"
                checkButtonEffect()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvTextNumberAddPhoneBook.text = binding.etTextAddPhoneBook.text.toString().length.toString() + "/30 자"
                checkButtonEffect()
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.tvTextNumberAddPhoneBook.text = binding.etTextAddPhoneBook.text.toString().length.toString() + "/30 자"
                checkButtonEffect()
            }

        })
    }

    private fun addValidInfoToDB(){ //정보 입력 시 필수사항 누락 경우 알림 
        if (binding.etNameAddPhoneBook.text.toString().isEmpty() || binding.etPhoneNumberAddPhoneBook.text.toString().isEmpty()){
            if (binding.etNameAddPhoneBook.text.toString().isEmpty() && binding.etPhoneNumberAddPhoneBook.text.toString().isEmpty()){
                Toast.makeText(this,"저장할 정보를 모두 입력해 주세요", Toast.LENGTH_SHORT).show()
                binding.tvNameMustHaveAddPhoneBook.setTextColor(Color.parseColor("#FF0000"))
                binding.tvPhoneNumberMustHaveAddPhoneBook.setTextColor(Color.parseColor("#FF0000"))
            }
            else if (binding.etPhoneNumberAddPhoneBook.text.toString().isEmpty()){
                Toast.makeText(this,"저장할 번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
                binding.tvPhoneNumberMustHaveAddPhoneBook.setTextColor(Color.parseColor("#FF0000"))
            }
            else{
                Toast.makeText(this,"저장할 이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
                binding.tvNameMustHaveAddPhoneBook.setTextColor(Color.parseColor("#FF0000"))
            }
        }
        else{       //정보가 모두 기입 됐을 시
            if (intent.getStringExtra("name") == null){   //순수 연락처 추가인지 연락처 수정을 위한 경우인지 판별하기 위함

                // '+'버튼을 눌러서 본 Activity로 전환해온 경우로 판별 -> 연락처 추가 DB에 새로운 데이터 추가
                if (binding.etTextAddPhoneBook.text.toString().equals("")){
                    addPerson(binding.etNameAddPhoneBook.text.toString(), binding.etPhoneNumberAddPhoneBook.text.toString(), "")
                }
                else {
                    addPerson(binding.etNameAddPhoneBook.text.toString(),binding.etPhoneNumberAddPhoneBook.text.toString(),binding.etTextAddPhoneBook.text.toString())
                }
                Toast.makeText(this,"연락처를 저장하였습니다", Toast.LENGTH_SHORT).show()
                Log.d("데이타 베이스 확인 ! ! !", personInfoDatabase.personInfoDao().getPersonList().toString())
                finish()
            }
            else{       //수정 icon 눌러서 본 Activity로 전환해온 경우로 판별 -> 수정된 내용을 EmergencyCallActivity로 보냄

                intent.putExtra("name", binding.etNameAddPhoneBook.text.toString())
                intent.putExtra("phoneNumber", binding.etPhoneNumberAddPhoneBook.text.toString())
                intent.putExtra("text", binding.etTextAddPhoneBook.text.toString())
                intent.putExtra("personId",intent.getIntExtra("personId",0))
                intent.putExtra("position",intent.getIntExtra("position",0))
                setResult(RESULT_OK, intent)

                Toast.makeText(this,"연락처를 수정하였습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun addPerson(name : String, phoneNumber : String, text : String){
        personInfoDatabase.personInfoDao().insertPerson(
            PersonInfo(name, phoneNumber, text)
        )
        Log.d("PersonInfoDataBase", personInfoDatabase.personInfoDao().getPersonList().toString())
    }

    private fun initData(){
        //ViewHolder의 수정 icon을 눌러서 본 Activity로 왔을 경우 해당 ViewHolder의 연락처 정보로 EditText들 setUp

        if (intent.getStringExtra("name") == null){
            Log.d("!!!", "추가할 데이터")
            return
        }
        Log.d("!!!", "수정할 데이터")

        val name = intent.getStringExtra("name")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val text = intent.getStringExtra("text")

        binding.etNameAddPhoneBook.setText(name)
        binding.etPhoneNumberAddPhoneBook.setText(phoneNumber)
        binding.tvNameNumberAddPhoneBook.setText(name!!.length.toString() + "/8 자")
        binding.etTextAddPhoneBook.setText(text)
        binding.tvTextNumberAddPhoneBook.setText(text!!.length.toString() + "/30 자")
    }

    private fun checkButtonEffect(){
        //추가할 연락처 정보가 모두 정상적으로 기입 됐는지 시각적으로 알려주기 위해 저장 버튼을 상황별로 바꿈
        if (binding.etNameAddPhoneBook.text.toString().equals("") || binding.etPhoneNumberAddPhoneBook.text.toString().equals("")){
            binding.tvSaveButtonAddPhoneBook.setBackgroundDrawable(resources.getDrawable(R.drawable.save_button_uncliked_effect))
        }
        else{
            binding.tvSaveButtonAddPhoneBook.setBackgroundDrawable(resources.getDrawable(R.drawable.save_button_effect))
        }
    }

}