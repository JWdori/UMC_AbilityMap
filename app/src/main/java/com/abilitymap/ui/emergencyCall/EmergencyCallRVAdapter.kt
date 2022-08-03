package com.abilitymap.ui.emergencyCall

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abilitymap.data.personInfo.PersonInfo
import com.abilitymap.R
import com.abilitymap.databinding.ItemEmergencyCallBinding

class EmergencyCallRVAdapter(): RecyclerView.Adapter<EmergencyCallRVAdapter.ViewHolder>() {


    //RecyclerView의 ViewHolder의 data를 초기화하기 위해
    //DB에서 가져온 data들을 담을 ArrayList<PersonInfo>형의 local 변수
    private val personInfo = ArrayList<PersonInfo>()

    lateinit var binding: ItemEmergencyCallBinding
    lateinit var mContext: Context
    lateinit var name : String
    lateinit var phoneNumber: String
    lateinit var recyclerView : RecyclerView


    interface MyItemClickListener{  //특정 event 발생 시 Caller인 EmergencyCallActivity로 하여금 해당 작업을 수행하게 함
        fun onResetViewHolder(holder: ViewHolder, position: Int, size : Int)
        fun onRemovePerson(PersonId : Int)
        fun onItemClicked(personId : Int, position : Int)
        fun onUpdatePerson(personId : Int, personInfo : PersonInfo, position : Int)
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener  //EmergencyCallActivity와 연결
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        //데이터를 담을 ViewHolder 생성
        binding = ItemEmergencyCallBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(personInfo[position], position) //ViewHoldr binding

        holder.binding.layoutEmergencyCall.setOnClickListener {
            //클릭된 layout이 직전 클릭된 layout과 동일할 시 선택 표시 Off
            if(holder.binding.flag.text.toString().equals("true")){
                resetViewHolder(holder.binding, mContext.getDrawable(R.drawable.rectangle)!!, "#000000", "#000000", true, position)

                mItemClickListener.onItemClicked(-1, -1)
            }
            //직전 layout과 다른 layout이 클릭 되었을 시 해당 layout 배경 변경
            else{
                resetViewHolder(holder.binding, mContext.getDrawable(R.drawable.rectangle_clicked)!!, "#ffffff", "#ffffff", false, position)

                mItemClickListener.onItemClicked(personInfo[position].personId, position)

                Log.d("POSITION OF RV : ", position.toString())

                for (i : Int in 0 until itemCount){    //직전 클릭된 layout 배경 reset, 선택 표시 Off

                    try{
                        Log.d("POSITION Test : ", i.toString())
                        val tHolder : ViewHolder = (recyclerView.findViewHolderForAdapterPosition(i) as ViewHolder?)!!
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

        //삭제 icon 클릭 시
        holder.binding.ivDeleteEmergencyCall.setOnClickListener {
            showDialog(holder, position)
        }

        holder.binding.ivDeleteEmergencyCallWhite.setOnClickListener {
            showDialog(holder, position)
        }

        //수정 icon 클릭 시
        holder.binding.ivUpdateEmergencyCallWhite.setOnClickListener {
            mItemClickListener.onUpdatePerson(personInfo[position].personId, personInfo[position], position)
        }

        holder.binding.ivUpdateEmergencyCall.setOnClickListener {
            mItemClickListener.onUpdatePerson(personInfo[position].personId, personInfo[position], position)
        }

    }

    override fun getItemCount(): Int = personInfo.size  //ViewHolder item의 전체 개수

    @SuppressLint("NotifyDataSetChanged")
    fun addPersonInfo(personInfo: ArrayList<PersonInfo>){   //DB내의 데이터로 local 데이터 동기화
        this.personInfo.clear()
        this.personInfo.addAll(personInfo)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removePerson(position : Int){   //local 데이터 항목 삭제
        this.personInfo.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePerson(name: String, phoneNumber: String, text : String, position : Int){ //local 데이터 항목 update
        this.personInfo[position].name = name
        this.personInfo[position].phoneNumber = phoneNumber
        this.personInfo[position].text = text
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEmergencyCallBinding):RecyclerView.ViewHolder(binding.root){
        //RecyclerView에 보여질 item 객체인 ViewHolder

        fun bind(personInfo: PersonInfo, position : Int){
            //ViewHolder 바인딩은 DB에서 가져온 ArrayList<PersonInfo>를 local 데이터에 옮겨 이를 바탕으로 이루어짐
            binding.tvNameEmergencyCall.text = personInfo.name
            binding.tvPhoneNumberEmergencyCall.text = personInfo.phoneNumber
        }
    }

    fun showDialog(holder: ViewHolder, position : Int){
        //삭제 확인을 재차 묻기 위한 Dialog

        val dialog : Dialog = InfoDialog(mContext)
        dialog.show()

        val text = dialog.findViewById<TextView>(R.id.text_dialog)

        //삭제 대상인 연락처의 이름을 강조하기 위해 해당 text bold처리
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
        //선택된 연락처의 ViewHolder를 표시 On 및 이전 선택 연락처 표시 Off

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
