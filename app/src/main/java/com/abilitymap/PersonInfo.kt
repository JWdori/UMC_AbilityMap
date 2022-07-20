package com.abilitymap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personInfoTable")
data class PersonInfo(
    var name : String? = "",
    var phoneNumber : String? = "",
    var text : String? = "",
){
    @PrimaryKey(autoGenerate = true) var personId : Int = 0
}
