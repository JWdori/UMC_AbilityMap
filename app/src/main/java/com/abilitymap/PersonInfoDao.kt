package com.abilitymap

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonInfoDao {

    @Query("Select * From PersonInfoTable")
    fun getPerson() : List<PersonInfo>

    @Insert()
    fun insertPerson(person : PersonInfo)

    @Query("Delete From PersonInfoTable Where personId =:personId")
    fun deletePerson(personId : Int)

}