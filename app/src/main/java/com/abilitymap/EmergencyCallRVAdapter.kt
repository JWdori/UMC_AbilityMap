package com.abilitymap

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abilitymap.databinding.ItemEmergencyCallBinding

class EmergencyCallRVAdapter(): RecyclerView.Adapter<EmergencyCallRVAdapter.ViewHolder>() {

    private val personInfo = ArrayList<PersonInfo>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EmergencyCallRVAdapter.ViewHolder {
        val binding: ItemEmergencyCallBinding = ItemEmergencyCallBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmergencyCallRVAdapter.ViewHolder, position: Int) {
        holder.bind(personInfo[position])
    }

    override fun getItemCount(): Int = personInfo.size

    @SuppressLint("NotifyDataSetChanged")
    fun addPersonInfo(personInfo: ArrayList<PersonInfo>){
        this.personInfo.clear()
        this.personInfo.addAll(personInfo)

        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEmergencyCallBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(personInfo: PersonInfo){
            binding.tvNameEmergencyCall.text = personInfo.name
            binding.tvPhoneNumberEmergencyCall.text = personInfo.phoneNumber
        }
    }
}