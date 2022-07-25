package com.abilitymap

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abilitymap.databinding.ItemEmergencyCallBinding
import java.lang.reflect.Type

class EmergencyCallRVAdapter(): RecyclerView.Adapter<EmergencyCallRVAdapter.ViewHolder>() {

    private val personInfo = ArrayList<PersonInfo>()
    lateinit var binding: ItemEmergencyCallBinding
    lateinit var mContext: Context
    lateinit var name : String
    lateinit var phoneNumber: String
    lateinit var recyclerView : RecyclerView


    interface MyItemClickListener{
        fun onResetViewHolder(holder: ViewHolder, position: Int, size : Int)
        fun onRemovePerson(PersonId : Int)
        fun onItemClicked(personId : Int, position : Int)
        fun onUpdatePerson(personId : Int, personInfo : PersonInfo, position : Int)
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

        holder.binding.layoutEmergencyCall.setOnClickListener {
            //클릭된 layout이 직전 클릭된 layout과 동일할 시
            if(holder.binding.flag.text.toString().equals("true")){
                resetViewHolder(holder.binding, mContext.getDrawable(R.drawable.rectangle)!!, "#000000", "#000000", true, position)

                mItemClickListener.onItemClicked(-1, -1)
            }
            //직전 layout과 다른 layout이 클릭 되었을 시 해당 layout 배경 변경
            else{
                resetViewHolder(holder.binding, mContext.getDrawable(R.drawable.rectangle_clicked)!!, "#ffffff", "#ffffff", false, position)

                mItemClickListener.onItemClicked(personInfo[position].personId, position)

                for (i : Int in 0 until itemCount-1){    //직전 클릭된 layout 배경 reset

                    try{
                        val tHolder : EmergencyCallRVAdapter.ViewHolder = (recyclerView.findViewHolderForAdapterPosition(i) as ViewHolder?)!!
                        if (i != position && tHolder.binding.flag.text.toString().equals("true")){         //클릭 된 것을 제외한 view들 원 상태로 복귀
                            resetViewHolder(tHolder.binding, mContext.getDrawable(R.drawable.rectangle)!!, "#000000", "#000000", true, i)
                            break
                        }
                    }
                    catch(e : Exception){
                    }
                }

            }
        }

        holder.binding.ivDeleteEmergencyCall.setOnClickListener {
            showDialog(holder, position)
        }

        holder.binding.ivDeleteEmergencyCallWhite.setOnClickListener {
            showDialog(holder, position)
        }

        holder.binding.ivUpdateEmergencyCallWhite.setOnClickListener {
            mItemClickListener.onUpdatePerson(personInfo[position].personId, personInfo[position], position)
        }

        holder.binding.ivUpdateEmergencyCall.setOnClickListener {
            mItemClickListener.onUpdatePerson(personInfo[position].personId, personInfo[position], position)
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

    @SuppressLint("NotifyDataSetChanged")
    fun updatePerson(name: String, phoneNumber: String, text : String, position : Int){
        this.personInfo[position].name = name
        this.personInfo[position].phoneNumber = phoneNumber
        this.personInfo[position].text = text
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEmergencyCallBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(personInfo: PersonInfo, position : Int){
            binding.tvNameEmergencyCall.text = personInfo.name
            binding.tvPhoneNumberEmergencyCall.text = personInfo.phoneNumber
        }
    }

    fun showDialog(holder: EmergencyCallRVAdapter.ViewHolder, position : Int){
        val dialog : Dialog = InfoDialog(mContext)
        dialog.show()

        val text = dialog.findViewById<TextView>(R.id.text_dialog)

        //text bold처리
        val string : String = "\""+personInfo[position].name!!+"\""+text.text.toString()
        val builder = SpannableStringBuilder(string)
        val boldSpan = StyleSpan(Typeface.BOLD)
        //boldSpan, startIndex, endIndex, EXCLUSIVE_EXCLUSIVE
        builder.setSpan(boldSpan, string.indexOf("\""), string.lastIndexOf("\"")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        text.text = builder


        val yesButton = dialog.findViewById<TextView>(R.id.tv_yes_dialog)
        val noButton = dialog.findViewById<TextView>(R.id.tv_no_dialog)

        yesButton.setOnClickListener {      //삭제 "예" 눌렀을 시 선택됐던 연락처 초기화
            mItemClickListener.onRemovePerson(personInfo[position].personId)
            removePerson(position)
            mItemClickListener.onResetViewHolder(holder, position, personInfo.size)
            dialog.dismiss()
            Toast.makeText(mContext, "선택하신 연락처를 삭제하였습니다", Toast.LENGTH_SHORT).show()
        }

        noButton.setOnClickListener { dialog.dismiss() }
    }

    private fun resetViewHolder(binding : ItemEmergencyCallBinding, img : Drawable, nameColor : String, pNColor : String, isClicked : Boolean, position : Int){
        binding.layoutEmergencyCall.setBackgroundDrawable(img)
        binding.tvNameEmergencyCall.setTextColor(Color.parseColor(nameColor))
        binding.tvPhoneNumberEmergencyCall.setTextColor(Color.parseColor(pNColor))
        if (!isClicked){
            binding.ivDeleteEmergencyCall.visibility = View.INVISIBLE
            binding.ivDeleteEmergencyCallWhite.visibility = View.VISIBLE
            binding.ivUpdateEmergencyCall.visibility = View.INVISIBLE
            binding.ivUpdateEmergencyCallWhite.visibility = View.VISIBLE
            binding.flag.setText("true")
        }
        else{
            binding.ivDeleteEmergencyCall.visibility = View.VISIBLE
            binding.ivDeleteEmergencyCallWhite.visibility = View.INVISIBLE
            binding.ivUpdateEmergencyCall.visibility = View.VISIBLE
            binding.ivUpdateEmergencyCallWhite.visibility = View.INVISIBLE
            binding.flag.setText("false")
        }
    }


}
