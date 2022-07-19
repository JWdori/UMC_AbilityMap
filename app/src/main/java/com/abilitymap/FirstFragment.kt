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
import com.abilitymap.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    lateinit var binding : FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init(){
        val title : String = binding.tvFragmentFirst.text.toString()

        var builder = SpannableStringBuilder(title)
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, binding.tvFragmentFirst.text.indexOf("이동권과"), binding.tvFragmentFirst.text.indexOf("이동권과")+7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvFragmentFirst.text = builder
    }

}