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
import com.abilitymap.databinding.FragmentMenuBookThirdBinding

class MenuBookThirdFragment : Fragment() {

    lateinit var binding : FragmentMenuBookThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBookThirdBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init(){
        val title : String = binding.tvFragmentMenuBookThird.text.toString()

        var builder = SpannableStringBuilder(title)
        val boldSpan = StyleSpan(Typeface.BOLD)
        val sizeSpan = RelativeSizeSpan(0.8f)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#707070"))
        builder.setSpan(boldSpan, binding.tvFragmentMenuBookThird.text.indexOf("긴급"), binding.tvFragmentMenuBookThird.text.indexOf("긴급")+6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(sizeSpan, binding.tvFragmentMenuBookThird.text.lastIndexOf("긴급"), binding.tvFragmentMenuBookThird.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(colorSpan, binding.tvFragmentMenuBookThird.text.lastIndexOf("긴급"), binding.tvFragmentMenuBookThird.text.toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvFragmentMenuBookThird.text = builder
    }


}