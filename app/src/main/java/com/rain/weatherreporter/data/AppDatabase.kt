package com.rain.weatherreporter.data
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = arrayOf(WeatherResult::class), version = 3)
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun itemDao(): WeatherDAO
//
//    companion object {
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            if (INSTANCE == null) {
//                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                    AppDatabase::class.java, "weather.db")
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//            return INSTANCE!!
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
//}