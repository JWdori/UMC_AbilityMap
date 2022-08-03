package com.abilitymap.ui.menuBook

import android.os.Bundle
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



        return binding.root
    }


}