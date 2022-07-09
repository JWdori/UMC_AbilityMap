package com.abilitymap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personInfoTable")
data class PersonInfo(
    @PrimaryKey(autoGenerate = false) var personId : Int = 0,
    var name : String? = "",
    var phoneNumber : String? = ""
)
