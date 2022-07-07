package com.abilitymap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilitymap.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    lateinit var binding : ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
        initRecyclerView()

    }

    private fun initClickListener(){
        binding.ivNotGoBack.setOnClickListener { finish() }
    }

    private fun initRecyclerView(){
        val notificationRVAdapter = NotificationRVAdapter()
        binding.rvNotification.adapter = notificationRVAdapter
        binding.rvNotification.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        var news = ArrayList<News>()
        var new1 = News()
        var new2 = News()
        var new3 = News()
        new1.title = "어빌리티맵에 오신 것을 환영합니다 ^o^"
        new1.date = "2022/06/30"
        new2.title = "이용 설명서 보러가기"
        new2.date = "2022/06/30"
        new3.title = "어빌리티맵 버전 1.0 공개"
        new3.date = "11:30 am"
        news.add(new1)
        news.add(new2)
        news.add(new3)
        news.add(new1)
        news.add(new2)
        news.add(new3)
        news.add(new1)
        news.add(new2)
        news.add(new3)
        news.add(new1)
        news.add(new2)
        news.add(new3)

        notificationRVAdapter.addNews(news)

    }

}