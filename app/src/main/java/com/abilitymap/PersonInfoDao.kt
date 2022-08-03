package com.abilitymap

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonInfoDao {   //DB내 데이터 변경을 위한 Data Access Object

    @Query("Select * From PersonInfoTable")
    fun getPersonList() : List<PersonInfo>  //PersonInfo List 가져오기

    @Update()
    fun update(personInfo : PersonInfo)

    @Insert()
    fun insertPerson(person : PersonInfo)

    @Query("Delete From PersonInfoTable Where personId =:personId")
    fun deletePerson(personId : Int)    //해당 personId의 PersonInfo 객체 삭제

    @Query("Update PersonInfoTable Set name =:name, phoneNumber =:phoneNumber, text =:text Where personId =:personId")
    fun updatePerson(name : String, phoneNumber : String, text : String, personId : Int)    //해당 personId의 PersonInfo 객체 정보 수정

    @Query("Select name From PersonInfoTable Where personId =:personId")
    fun getName(personId : Int) : String    //해당 personId의 PersonInfo 이름 가져오기

    @Query("Select phoneNumber From PersonInfoTable Where personId =:personId")
    fun getPhoneNumber(personId : Int) : String //해당 personId의 PersonInfo 전화번호 가져오기

    @Query("Select text From PersonInfoTable Where personId =:personId")
    fun getText(personId : Int) : String    //해당 personId의 text 이름 가져오기
}