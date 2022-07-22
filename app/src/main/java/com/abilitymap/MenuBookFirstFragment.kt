package com.abilitymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abilitymap.databinding.FragmentMenuBookFirstBinding

class MenuBookFirstFragment : Fragment() {

    lateinit var binding : FragmentMenuBookFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBookFirstBinding.inflate(inflater, container, false)

        return binding.root
    }


}