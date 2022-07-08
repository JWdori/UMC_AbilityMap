package com.abilitymap

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonInfo::class], version = 1)
abstract class PersonInfoDatabase : RoomDatabase(){
    abstract fun personInfoDao(): PersonInfoDao

    companion object{
        private var instance : PersonInfoDatabase? = null

        @Synchronized
        fun getInstance(context: Context):PersonInfoDatabase?{
            if(instance==null){
                synchronized(PersonInfoDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonInfoDatabase::class.java,
                        "personInfo-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}