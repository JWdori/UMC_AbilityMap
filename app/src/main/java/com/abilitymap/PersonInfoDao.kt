package com.abilitymap

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonInfoDao {

    @Query("Select * From PersonInfoTable")
    fun getPersonList() : List<PersonInfo>

    @Update()
    fun update(personInfo : PersonInfo)

    @Insert()
    fun insertPerson(person : PersonInfo)

    @Query("Delete From PersonInfoTable Where personId =:personId")
    fun deletePerson(personId : Int)

    @Query("Update PersonInfoTable Set name =:name, phoneNumber =:phoneNumber Where personId =:personId")
    fun updatePerson(name : String, phoneNumber : String, personId : Int)

    @Query("Select phoneNumber From PersonInfoTable Where personId =:personId")
    fun getPhoneNumber(personId : Int) : String

    @Query("Select text From PersonInfoTable Where personId =:personId")
    fun getText(personId : Int) : String
}