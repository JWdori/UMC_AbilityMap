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
import com.abilitymap.databinding.FragmentFourthBinding

class FourthFragment : Fragment() {

    lateinit var binding : FragmentFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init(){
        val title : String = binding.tvFragmentFourth.text.toString()

        var builder = SpannableStringBuilder(title)
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, binding.tvFragmentFourth.text.toString().indexOf("제보"), binding.tvFragmentFourth.text.toString().indexOf("제보")+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvFragmentFourth.text = builder
    }

}