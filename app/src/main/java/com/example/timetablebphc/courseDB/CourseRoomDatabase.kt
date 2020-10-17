package com.example.timetablebphc.courseDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.timetablebphc.dao.TimeTableDao

@Database(entities = [Course::class, Quiz ::class], version = 1)
@TypeConverters(Converters::class)
abstract class CourseRoomDatabase : RoomDatabase() {

    abstract fun timeTableDao(): TimeTableDao

    companion object {
        @Volatile
        private var INSTANCE: CourseRoomDatabase? = null

        fun getInstance(context: Context): CourseRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CourseRoomDatabase::class.java,
                        "timetable_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
