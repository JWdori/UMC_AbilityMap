package com.abilitymap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuBookVPAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> MenuBookFirstFragment()
            1 -> MenuBookSecondFragment()
            2 -> MenuBookThirdFragment()
            else -> MenuBookFourthFragment()
        }

    }

}