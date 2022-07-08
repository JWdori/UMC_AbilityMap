package com.abilitymap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilitymap.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNotificationBinding
    private lateinit var notificationRVAdapter : NotificationRVAdapter
    private lateinit var news : ArrayList<News>

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
        notificationRVAdapter = NotificationRVAdapter()
        binding.rvNotification.adapter = notificationRVAdapter
        binding.rvNotification.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        initDB()

    }

    private fun initDB(){
        news = ArrayList<News>()
        news.add(News(news.size, "어빌리티맵 버전 1.0 공개", "2022/06/30"))
        news.add(News(news.size, "이용 설명서 보러가기", "2022/06/30"))
        news.add(News(news.size, "어빌리티맵에 오신 것을 환영합니다 ^o^", "2022/06/30"))

        notificationRVAdapter.addNews(news)
    }

}