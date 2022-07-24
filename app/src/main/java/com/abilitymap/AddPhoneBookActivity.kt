package com.abilitymap

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityAddPhoneBookBinding
import okhttp3.internal.Internal.instance

class AddPhoneBookActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddPhoneBookBinding
    lateinit var context : Context
    private lateinit var personInfoDatabase: PersonInfoDatabase

//    init{
//        instance = this
//    }
//    companion object {
//        lateinit var instance: AddPhoneBookActivity
//        fun applicationContext() : Context {
//            return instance.applicationContext
//        }
//    }
//
//    interface MyItemClickListener{
//        fun onClick()
//    }
//
//    private lateinit var mItemClickListener : MyItemClickListener
//
//    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
//        mItemClickListener = itemClickListener
//    }


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
        binding.ivArrowBackAddPhoneBook.setOnClickListener { finish() }
        binding.tvSaveButtonAddPhoneBook.setOnClickListener {
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
            else{       //데이터 수정
//                Log.d("Item ID", intent.getIntExtra("position", 0).toString())
//                Log.d("수정된 Name", binding.etNameAddPhoneBook.text.toString())
//                Log.d("수정된 PhoneNumber",binding.etPhoneNumberAddPhoneBook.text.toString())
////                personInfoDatabase.personInfoDao().updatePerson(binding.etNameAddPhoneBook.text.toString(), binding.etPhoneNumberAddPhoneBook.text.toString(), intent.getIntExtra("position", 0))
////                Log.d("DB 수정 후", personInfoDatabase.personInfoDao().getPersonList().toString())
////                finish()
////                mItemClickListener.onClick()
//
//                Log.d("DB 수정 전", personInfoDatabase.personInfoDao().getPersonList().toString())
//                personInfoDatabase.personInfoDao().updatePerson(binding.etNameAddPhoneBook.text.toString(), binding.etPhoneNumberAddPhoneBook.text.toString(), intent.getIntExtra("position", 0))
//                Log.d("DB 수정 후", personInfoDatabase.personInfoDao().getPersonList().toString())

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
        if (binding.etNameAddPhoneBook.text.toString().equals("") || binding.etPhoneNumberAddPhoneBook.text.toString().equals("")){
            binding.tvSaveButtonAddPhoneBook.setBackgroundDrawable(resources.getDrawable(R.drawable.save_button_uncliked_effect))
        }
        else{
            binding.tvSaveButtonAddPhoneBook.setBackgroundDrawable(resources.getDrawable(R.drawable.save_button_effect))
        }
    }

}