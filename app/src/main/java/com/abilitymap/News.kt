package com.abilitymap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableNews")
data class News(
    @PrimaryKey(autoGenerate = false) var id :Int = 0,
    var title : String? = "",
    var date : String? = ""
)
