package com.abilitymap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abilitymap.databinding.*

class OnboardingVPAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

//        val idx : Int = getPos(position)

        return when(position){
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            else -> FourthFragment()
        }
    }

//    private fun getPos(position: Int) : Int{
//        return position % 4
//    }

}