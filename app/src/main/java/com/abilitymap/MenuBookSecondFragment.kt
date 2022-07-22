package com.abilitymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abilitymap.databinding.FragmentMenuBookSecondBinding

class MenuBookSecondFragment : Fragment() {

    lateinit var binding : FragmentMenuBookSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBookSecondBinding.inflate(inflater, container, false)

        return binding.root
    }


}