package com.abilitymap

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityAddPhoneBookBinding

class AddPhoneBookActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddPhoneBookBinding
    private lateinit var personInfoDatabase: PersonInfoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPhoneBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personInfoDatabase = PersonInfoDatabase.getInstance(this)!!
        Log.d("PersonInfoDataBase", personInfoDatabase.personInfoDao().getPersonList().toString())
        initData()
        initClickListener()

    }

    private fun initClickListener(){
        binding.ivArrowBackAddPhoneBook.setOnClickListener { finish() }
        binding.tvSaveButtonAddPhoneBook.setOnClickListener {
            addValidInfoToDB()
        }
        binding.etNameAddPhoneBook.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvNameNumberAddPhoneBook.text = "0/8 자"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvNameNumberAddPhoneBook.text = binding.etNameAddPhoneBook.text.toString().length.toString() + "/8 자"
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.tvNameNumberAddPhoneBook.text = binding.etNameAddPhoneBook.text.toString().length.toString() + "/8 자"
            }

        })
    }

    private fun addValidInfoToDB(){
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
            if (intent.getStringExtra("name") == null){     //데이터 추가
                personInfoDatabase.personInfoDao().insertPerson(
                    PersonInfo(
                        binding.etNameAddPhoneBook.text.toString(), binding.etPhoneNumberAddPhoneBook.text.toString()
                    )
                )
                Log.d("DB 추가 후", personInfoDatabase.personInfoDao().getPersonList().toString())
                finish()
            }
            else{       //데이터 수정
                Log.d("DB", intent.getIntExtra("position", 0).toString())
                Log.d("DB", binding.etNameAddPhoneBook.text.toString())
                Log.d("DB",binding.etPhoneNumberAddPhoneBook.text.toString())
                personInfoDatabase.personInfoDao().updatePerson(binding.etNameAddPhoneBook.text.toString(), binding.etPhoneNumberAddPhoneBook.text.toString(), intent.getIntExtra("position", 0))
                Log.d("DB 수정 후", personInfoDatabase.personInfoDao().getPersonList().toString())
                finish()
            }
        }
    }

    private fun initData(){

        if (intent.getStringExtra("name") == null){
            Log.d("!!!", "추가할 데이터")
            return
        }
        Log.d("!!!", "수정할 데이터")

        val name = intent.getStringExtra("name")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val position = intent.getIntExtra("position", 0)

        binding.etNameAddPhoneBook.setText(name)
        binding.etPhoneNumberAddPhoneBook.setText(phoneNumber)
        binding.tvNameNumberAddPhoneBook.setText(name?.length.toString() + "/8 자")
    }

}