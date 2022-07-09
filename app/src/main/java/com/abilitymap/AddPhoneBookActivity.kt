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
            personInfoDatabase.personInfoDao().insertPerson(
                PersonInfo(
                    binding.etNameAddPhoneBook.toString(), binding.etPhoneNumberAddPhoneBook.toString()
                )
            )
            Toast.makeText(this,"연락처에 추가되었습니다", Toast.LENGTH_SHORT).show()
            Log.d("DB", personInfoDatabase.personInfoDao().getPersonList().toString())
        }
    }

}