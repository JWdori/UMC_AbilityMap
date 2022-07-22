package com.abilitymap

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abilitymap.databinding.ItemNotificationBinding

class NotificationRVAdapter(): RecyclerView.Adapter<NotificationRVAdapter.ViewHolder>() {

    private val news = ArrayList<News>()

    interface MyItemClickListener{
        fun onClick(position: Int)
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NotificationRVAdapter.ViewHolder {
        //item view 객체 재활용 위한 VH 생성
        val binding:ItemNotificationBinding = ItemNotificationBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //item view 객체 message passing
    }

    override fun onBindViewHolder(holder: NotificationRVAdapter.ViewHolder, position: Int) {
        //view holder로부터 data binding
        holder.bind(news[position])

        holder.binding.layoutItemNotification.setOnClickListener{
            mItemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = news.size    //data set 크기 알려줌 recyclerview에게 마지막 언제인지 알려줌

    @SuppressLint("NotifyDataSetChanged")
    fun addNews(news: ArrayList<News>){       //기존 news 초기화 및 받아온 news 로 update
        this.news.clear()
        this.news.addAll(news)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemNotificationBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(news: News){
            binding.tvTitle.text = news.title
            binding.tvDate.text = news.date
        }
    }

}