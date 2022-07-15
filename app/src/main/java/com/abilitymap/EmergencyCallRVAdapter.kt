package com.abilitymap

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abilitymap.databinding.ItemEmergencyCallBinding

class EmergencyCallRVAdapter(): RecyclerView.Adapter<EmergencyCallRVAdapter.ViewHolder>() {

    private val personInfo = ArrayList<PersonInfo>()
    lateinit var binding: ItemEmergencyCallBinding
    lateinit var mContext: Context
    lateinit var name : String
    lateinit var phoneNumber: String
    var position : Int = -1


    interface MyItemClickListener{
        fun onRemovePerson(PersonId : Int)
        fun onItemClicked(personInfo: PersonInfo, position: Int)
        fun onUpdatePerson(PersonId : Int)
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EmergencyCallRVAdapter.ViewHolder {
        binding = ItemEmergencyCallBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmergencyCallRVAdapter.ViewHolder, position: Int) {
        holder.bind(personInfo[position], position)

        holder.binding.ivDeleteEmergencyCall.setOnClickListener {

        val dialog : Dialog = InfoDialog(mContext, personInfo[position].name!!)
        dialog.show()
        val yesButton = dialog.findViewById<TextView>(R.id.tv_yes_dialog)
        val noButton = dialog.findViewById<TextView>(R.id.tv_no_dialog)
        yesButton.setOnClickListener {
            mItemClickListener.onRemovePerson(personInfo[position].personId)
            removePerson(position)
            dialog.dismiss()
            Toast.makeText(mContext, "선택하신 연락처를 삭제하였습니다", Toast.LENGTH_SHORT).show()
        }
        noButton.setOnClickListener { dialog.dismiss() }


        }

        holder.binding.ivModifyEmergencyCall.setOnClickListener {
            mItemClickListener.onItemClicked(personInfo[position], position)
        }


//        mContext.setMyItemClickListener(object : AddPhoneBookActivity.MyItemClickListener{
//            override fun onClick() {
//                mItemClickListener.onUpdatePerson(personInfo[position].personId)
//                updatePerson(name, phoneNumber, position)
//                setModifiedData("", "", -1)
//            }
//
//        })
//        Log.d("2", "error")

//        AddPhoneBookActivity.instance.binding.tvSaveButtonAddPhoneBook.setOnClickListener {
//            mItemClickListener.onUpdatePerson(personInfo[position].personId)
//            updatePerson(this.name, this.phoneNumber, this.position)
//            setModifiedData("", "", -1)
//        }

    }

    override fun getItemCount(): Int = personInfo.size

    fun setModifiedData(name: String, phoneNumber: String, position: Int){
        this.name = name
        this.phoneNumber = phoneNumber
        this.position = position
    }

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

    @SuppressLint("NotifyDataSetChanged")
    fun updatePerson(name: String, phoneNumber: String, position : Int){
        this.personInfo.removeAt(position)
        this.personInfo.add(position, PersonInfo(name, phoneNumber))
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEmergencyCallBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(personInfo: PersonInfo, position : Int){
            binding.tvNameEmergencyCall.text = personInfo.name
            binding.tvPhoneNumberEmergencyCall.text = personInfo.phoneNumber
        }
    }


}
