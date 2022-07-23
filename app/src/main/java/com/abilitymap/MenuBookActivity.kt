package com.abilitymap

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.abilitymap.databinding.ActivityMenuBookBinding

class MenuBookActivity : FragmentActivity() {

    private lateinit var binding : ActivityMenuBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
        initViewPager()
    }

    private fun initClickListener(){
        binding.ivBackArrowMenuBook.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager(){
        val menuBookVPAdapter = MenuBookVPAdapter(this)

        binding.vpViewpagerMenuBook.adapter = menuBookVPAdapter
        binding.indicatorMenuBook.setViewPager(binding.vpViewpagerMenuBook)
        binding.indicatorMenuBook.createIndicators(4,0)
        binding.vpViewpagerMenuBook.setCurrentItem(0)

        binding.ivArrowLeftMenuBook.setOnClickListener {
            binding.vpViewpagerMenuBook.setCurrentItem(binding.vpViewpagerMenuBook.currentItem-1)
            checkSidePage()
        }

        binding.ivArrowRightMenuBook.setOnClickListener {
            binding.vpViewpagerMenuBook.setCurrentItem(binding.vpViewpagerMenuBook.currentItem+1)
            checkSidePage()
        }

        binding.vpViewpagerMenuBook.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                checkSidePage()
                if (positionOffsetPixels == 0){
                    binding.vpViewpagerMenuBook.setCurrentItem(position)
                }

            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.indicatorMenuBook.animatePageSelected(position)
            }
        })

    }

    private fun checkSidePage(){    //양쪽 끝 페이지일 시 회색 이미지로 변경
        if (binding.vpViewpagerMenuBook.currentItem==0){    //첫번째 페이지일 때 왼쪽 방향 아이콘 수정
            binding.ivArrowLeftMenuBook.setImageResource(R.drawable.icon_left_g)
        }
        else if(binding.vpViewpagerMenuBook.currentItem==3){  //마지막 페이지일 때 오른쪽 방향 아이콘 수정
            binding.ivArrowRightMenuBook.setImageResource(R.drawable.icon_right_g)
        }
        else{       //그 외 일 시 검은 방향 아이콘으로 원상 복구
            binding.ivArrowLeftMenuBook.setImageResource(R.drawable.icon_back_black)
            binding.ivArrowRightMenuBook.setImageResource(R.drawable.icon_right_b)
        }
    }

}