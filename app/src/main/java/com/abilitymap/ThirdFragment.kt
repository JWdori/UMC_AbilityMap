package com.abilitymap

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abilitymap.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(){

    lateinit var binding : FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init(){
        val title : String = binding.tvFragmentThird.text.toString()

        var builder = SpannableStringBuilder(title)
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, binding.tvFragmentThird.text.indexOf("문자"), binding.tvFragmentThird.text.indexOf("문자")+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvFragmentThird.text = builder
    }

}