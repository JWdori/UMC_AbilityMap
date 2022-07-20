package com.abilitymap

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abilitymap.databinding.ActivityChooseOptionBinding

class ChooseOptionActivity :AppCompatActivity() {

    private lateinit var binding : ActivityChooseOptionBinding
    private var isYes : Boolean = false //교통약자 선택했는지
    private var isNo : Boolean = false  //교통약자가 아닌 선택했는지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeText()
        initClickListener()
    }

    private fun checkClick(){
        if (isYes){         //교통약자 선택 시 sharedPreference 값 1
            putSPF(1)
        }
        else if(isNo){      //일반인 선택 시 sharedPreference 값 0
            putSPF(0)
        }
    }

    private fun putSPF(mode : Int){
        val spfOnBoarding : SharedPreferences = getSharedPreferences("mode", MODE_PRIVATE);
        val editor : SharedPreferences.Editor = spfOnBoarding.edit();
        editor.putInt("userMode", mode);
        if (mode == 1){
            editor.putString("text", "[긴급신고] 안전지도 모아도\n\n\"교통약자\"의 긴급 신고입니다!\n")
        }
        else{
            editor.putString("text","[긴급신고] 안전지도 모아도\n\n긴급 신고입니다!\n")
        }

        editor.apply();
        editor.commit();
    }

    private fun initClickListener(){
        binding.tvSelectButtonChooseOption.setOnClickListener {
            if (isYes == false && isNo == false){
                Toast.makeText(this,"하나의 선택지를 선택해 주세요", Toast.LENGTH_SHORT).show()
            }
            else{                       //선택지 하나가 선택되었을 시 onboarding화면 더 이상 안나오도록 하고
                checkClick()            // 이전 activity 종료 후 main activity로 이동

                val spfOnBoarding = getSharedPreferences("onBoarding", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = spfOnBoarding.edit()
                editor.putBoolean("isFirst", false)
                editor.apply()
                editor.commit() //이후 실행부터는 else문으로 가도록 isFirst == false로 지정


                startActivity(Intent(this, MainActivity::class.java))
                //메인 액티비티로 이동하기 까지 공백 시간 splash ??
                finish()
            }
        }
        binding.tvYesChooseOption.setOnClickListener {      //교통약자 선택 시
            if (isYes == false && isNo == false){
                binding.tvSelectButtonChooseOption.setBackgroundDrawable(resources.getDrawable(R.drawable.save_button_effect))
            }

            isYes = true

            if(isNo == true){
                binding.tvNoChooseOption.setBackgroundDrawable(resources.getDrawable(R.drawable.choose_option))
                binding.tvNoChooseOption.setTextColor(Color.parseColor("#000000"))

                isNo = false
            }
            binding.ivYesChooseOption.setBackgroundDrawable(resources.getDrawable(R.drawable.choose_option_clicked))
            binding.ivYesChooseOption.setImageResource(R.drawable.icon_onboarding_5_1)
            changeTextYesOption("#ffffff", "#e2e2e2")
        }
        binding.tvNoChooseOption.setOnClickListener {   //교통약자가 아닌 선택 시
            if (isYes == false && isNo == false){
                binding.tvSelectButtonChooseOption.setBackgroundDrawable(resources.getDrawable(R.drawable.save_button_effect))
            }

            isNo = true

            if (isYes == true){
                binding.ivYesChooseOption.setBackgroundDrawable(resources.getDrawable(R.drawable.choose_option))
                binding.ivYesChooseOption.setImageResource(R.drawable.icon_onboarding_5)

                changeTextYesOption("#000000", "#707070")

                isYes = false
            }

            binding.tvNoChooseOption.setBackgroundDrawable(resources.getDrawable(R.drawable.choose_option_clicked))
            binding.tvNoChooseOption.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private fun changeTextYesOption(colorTitle : String, colorSubTitle : String){
        val yesOption : String = binding.tvYesChooseOption.text.toString()

        val builder = SpannableStringBuilder(yesOption)
        val colorSpan = ForegroundColorSpan(Color.parseColor(colorTitle))
        val colorSpanSub = ForegroundColorSpan(Color.parseColor(colorSubTitle))
        val boldSpan = StyleSpan(Typeface.BOLD)
        val sizeBigSpan = RelativeSizeSpan(0.6f)

        builder.setSpan(sizeBigSpan, binding.tvYesChooseOption.text.indexOf("("), binding.tvYesChooseOption.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(boldSpan, binding.tvYesChooseOption.text.indexOf("교통약자"), binding.tvYesChooseOption.text.indexOf("교통약자")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(colorSpan, binding.tvYesChooseOption.text.indexOf("저는"), binding.tvYesChooseOption.text.indexOf(".")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(colorSpanSub, binding.tvYesChooseOption.text.indexOf("("), binding.tvYesChooseOption.text.indexOf(")")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvYesChooseOption.text = builder
    }

    private fun changeText(){   //text 부분 bold 처리
        val title : String = binding.tvTitleChooseOption.text.toString()
        val noOption : String = binding.tvNoChooseOption.text.toString()

        var builder = SpannableStringBuilder(title)
        val boldSpan = StyleSpan(Typeface.BOLD)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#707070"))
        val sizeBigSpan = RelativeSizeSpan(0.6f)

        builder.setSpan(sizeBigSpan, binding.tvTitleChooseOption.text.indexOf("※"), binding.tvTitleChooseOption.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(boldSpan, binding.tvTitleChooseOption.text.indexOf("교통약자"), binding.tvTitleChooseOption.text.indexOf("교통약자")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(colorSpan, binding.tvTitleChooseOption.text.indexOf("※"), binding.tvTitleChooseOption.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTitleChooseOption.text = builder


        changeTextYesOption("#000000", "#707070")


        builder = SpannableStringBuilder(noOption)

        builder.setSpan(boldSpan, binding.tvNoChooseOption.text.indexOf("아닙니다."), binding.tvNoChooseOption.text.indexOf("아닙니다")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvNoChooseOption.text = builder

    }

}