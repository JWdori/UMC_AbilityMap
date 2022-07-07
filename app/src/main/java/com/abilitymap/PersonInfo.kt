package com.abilitymap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tablePersonInfo")
data class PersonInfo(
    @PrimaryKey(autoGenerate = false) var id : Int = 0,
    var name : String? = "",
    var phoneNumber : String? = ""
)
