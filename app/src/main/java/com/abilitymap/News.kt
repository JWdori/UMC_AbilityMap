package com.abilitymap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableNews")
data class News(
    var title : String? = "",
    var date : String? = ""

){
    @PrimaryKey(autoGenerate = true) var id :Int = 0
}
