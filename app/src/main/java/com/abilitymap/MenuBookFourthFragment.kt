package com.abilitymap

import android.os.Bundle
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

        return binding.root
    }


}