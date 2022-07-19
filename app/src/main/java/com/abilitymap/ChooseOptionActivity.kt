package com.abilitymap

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
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
        val colorSpan = ForegroundColorSpan(Color.parseColor("#707070"))
        val sizeBigSpan = RelativeSizeSpan(0.6f)

        builder.setSpan(sizeBigSpan, binding.tvTitleChooseOption.text.indexOf("※"), binding.tvTitleChooseOption.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(boldSpan, binding.tvTitleChooseOption.text.indexOf("교통약자"), binding.tvTitleChooseOption.text.indexOf("교통약자")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(colorSpan, binding.tvTitleChooseOption.text.indexOf("※"), binding.tvTitleChooseOption.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTitleChooseOption.text = builder


        builder = SpannableStringBuilder(yesOption)

        builder.setSpan(sizeBigSpan, binding.tvYesChooseOption.text.indexOf("("), binding.tvYesChooseOption.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(colorSpan, binding.tvYesChooseOption.text.indexOf("(휠체어"), binding.tvYesChooseOption.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(boldSpan, binding.tvYesChooseOption.text.indexOf("교통약자"), binding.tvYesChooseOption.text.indexOf("교통약자")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvYesChooseOption.text = builder


        builder = SpannableStringBuilder(noOption)

        builder.setSpan(boldSpan, binding.tvNoChooseOption.text.indexOf("아닙니다."), binding.tvNoChooseOption.text.indexOf("아닙니다")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvNoChooseOption.text = builder

    }

}