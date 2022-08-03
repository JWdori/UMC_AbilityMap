package com.abilitymap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personInfoTable")
data class PersonInfo(      //연락처 item들의 정보를 나타낼 data class
    var name : String? = "",
    var phoneNumber : String? = "",
    var text : String? = "",
){
    @PrimaryKey(autoGenerate = true) var personId : Int = 0
}
