package com.abilitymap

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abilitymap.databinding.FragmentMenuBookFourthBinding

class MenuBookFourthFragment : Fragment() {

    lateinit var binding : FragmentMenuBookFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBookFourthBinding.inflate(inflater, container, false)

//        init()

        return binding.root
    }

//    private fun init(){
//        val title : String = binding.tvFragmentMenuBookFourth.text.toString()
//
//        var builder = SpannableStringBuilder(title)
//        val boldSpan = StyleSpan(Typeface.BOLD)
//        val sizeSpan = RelativeSizeSpan(0.8f)
//        val colorSpan = ForegroundColorSpan(Color.parseColor("#707070"))
//        builder.setSpan(boldSpan, binding.tvFragmentMenuBookFourth.text.indexOf("위험"), binding.tvFragmentMenuBookFourth.text.indexOf("이동권과")+7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        builder.setSpan(sizeSpan, binding.tvFragmentMenuBookFourth.text.indexOf("사진"), binding.tvFragmentMenuBookFourth.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        builder.setSpan(colorSpan, binding.tvFragmentMenuBookFourth.text.lastIndexOf("사진"), binding.tvFragmentMenuBookFourth.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        binding.tvFragmentMenuBookFourth.text = builder
//    }



}