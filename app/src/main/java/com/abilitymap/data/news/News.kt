package com.abilitymap.data.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableNews")
data class News(    //알림 페이지의 item들을 나타낼 data class
    var title : String? = "",
    var date : String? = ""

){
    @PrimaryKey(autoGenerate = true) var id :Int = 0
}
