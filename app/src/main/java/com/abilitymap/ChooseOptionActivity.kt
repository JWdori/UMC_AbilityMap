package com.abilitymap

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityChooseOptionBinding

class ChooseOptionActivity :AppCompatActivity() {

    private lateinit var binding : ActivityChooseOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeText()
    }


    private fun changeText(){   //text 부분 bold 처리
        val title : String = binding.tvTitleChooseOption.text.toString()
        val yesOption : String = binding.tvYesChooseOption.text.toString()
        val noOption : String = binding.tvNoChooseOption.text.toString()

        var builder = SpannableStringBuilder(title)
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, 4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTitleChooseOption.text = builder

        builder = SpannableStringBuilder(yesOption)
        builder.setSpan(boldSpan, 3, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvYesChooseOption.text = builder

        builder = SpannableStringBuilder(noOption)
        builder.setSpan(boldSpan, 9, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvNoChooseOption.text = builder
    }

}