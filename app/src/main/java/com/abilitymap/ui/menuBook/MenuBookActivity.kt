package com.abilitymap.ui.menuBook

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.abilitymap.R
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
        binding.ivBackArrowMenuBook.setOnClickListener { finish() }
        binding.ivCloseMenuBook.setOnClickListener { finish() }
    }

    private fun initViewPager(){
        //뷰페이저 초기화 및 연결

        val menuBookVPAdapter = MenuBookVPAdapter(this)

        binding.vpViewpagerMenuBook.adapter = menuBookVPAdapter
        binding.indicatorMenuBook.setViewPager(binding.vpViewpagerMenuBook)
        binding.indicatorMenuBook.createIndicators(4,0)
        binding.vpViewpagerMenuBook.setCurrentItem(0)

        //좌, 우 방향 클릭 시 화면 넘김 이루어 지도록 설정
        //가장자리에 위치한 페이지일 시 상황에 맞게 좌 우 방향 이미지 회색으로 변경하여
        //클릭 불가능함을 시각적으로 연출
        binding.ivArrowLeftMenuBook.setOnClickListener {
            binding.vpViewpagerMenuBook.setCurrentItem(binding.vpViewpagerMenuBook.currentItem-1)
            checkSidePage()
        }
        binding.ivArrowRightMenuBook.setOnClickListener {
            binding.vpViewpagerMenuBook.setCurrentItem(binding.vpViewpagerMenuBook.currentItem+1)
            checkSidePage()
        }

        //뷰페이저 스크롤 기능 구현 및 indicator와 동기화
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
            binding.ivArrowRightMenuBook.visibility = View.GONE
            binding.ivCloseMenuBook.visibility = View.VISIBLE
        }
        else{       //그 외 일 시 검은 방향 아이콘으로 원상 복구
            binding.ivArrowLeftMenuBook.setImageResource(R.drawable.icon_back_black)
            binding.ivArrowRightMenuBook.visibility = View.VISIBLE
            binding.ivCloseMenuBook.visibility = View.GONE
        }
    }

}