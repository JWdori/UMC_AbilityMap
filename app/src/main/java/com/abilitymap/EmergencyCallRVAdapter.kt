package com.abilitymap

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abilitymap.databinding.ItemEmergencyCallBinding

class EmergencyCallRVAdapter(): RecyclerView.Adapter<EmergencyCallRVAdapter.ViewHolder>() {

    private val personInfo = ArrayList<PersonInfo>()

    interface MyItemClickListener{
        fun onRemovePerson(PersonId : Int)
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EmergencyCallRVAdapter.ViewHolder {
        val binding: ItemEmergencyCallBinding = ItemEmergencyCallBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmergencyCallRVAdapter.ViewHolder, position: Int) {
        holder.bind(personInfo[position], position)

        holder.binding.ivDeleteEmergencyCall.setOnClickListener {
            mItemClickListener.onRemovePerson(personInfo[position].personId)
            removePerson(position)
        }

    }

    override fun getItemCount(): Int = personInfo.size

    @SuppressLint("NotifyDataSetChanged")
    fun addPersonInfo(personInfo: ArrayList<PersonInfo>){
        this.personInfo.clear()
        this.personInfo.addAll(personInfo)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removePerson(position : Int){
        this.personInfo.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEmergencyCallBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(personInfo: PersonInfo, position : Int){
            binding.tvNameEmergencyCall.text = personInfo.name
            binding.tvPhoneNumberEmergencyCall.text = personInfo.phoneNumber

            binding.ivModifyEmergencyCall.setOnClickListener {
                val intent = Intent(binding.root.context, AddPhoneBookActivity::class.java)
                intent.putExtra("name", personInfo.name)
                intent.putExtra("phoneNumber", personInfo.phoneNumber)
                intent.putExtra("position", position)
                intent.run { binding.root.context.startActivity(this) }
            }
        }
    }
}