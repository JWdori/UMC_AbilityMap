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
import com.abilitymap.databinding.FragmentSecondBinding

class SecondFragment : Fragment(){

    lateinit var binding : FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init(){
        val title : String = binding.tvFragmentSecond.text.toString()

        var builder = SpannableStringBuilder(title)
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, binding.tvFragmentSecond.text.toString().indexOf("유용한"), binding.tvFragmentSecond.text.toString().indexOf("유용한")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvFragmentSecond.text = builder
    }

}