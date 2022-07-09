package com.abilitymap

import android.os.Bundle
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
        initClickListener()

    }

    private fun initClickListener(){
        binding.ivArrowBackAddPhoneBook.setOnClickListener { finish() }
        binding.tvSaveButtonAddPhoneBook.setOnClickListener {
            addValidInfoToDB()
        }
    }

    private fun addValidInfoToDB(){
        if (binding.etNameAddPhoneBook.text.toString().isEmpty() || binding.etPhoneNumberAddPhoneBook.text.toString().isEmpty()){
            if (binding.etNameAddPhoneBook.text.toString().isEmpty() && binding.etPhoneNumberAddPhoneBook.text.toString().isEmpty())
                Toast.makeText(this,"저장할 정보를 모두 입력해 주세요", Toast.LENGTH_SHORT).show()
            else if (binding.etPhoneNumberAddPhoneBook.text.toString().isEmpty())
                Toast.makeText(this,"저장할 번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"저장할 이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
        }
        else{       //정보가 모두 기입 됐을 시
            personInfoDatabase.personInfoDao().insertPerson(
                PersonInfo(
                    binding.etNameAddPhoneBook.text.toString(), binding.etPhoneNumberAddPhoneBook.text.toString()
                )
            )
            finish()
            Log.d("DB", personInfoDatabase.personInfoDao().getPersonList().toString())
        }
    }


}