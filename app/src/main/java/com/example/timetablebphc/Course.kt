package com.example.timetablebphc

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.DayOfWeek

@Entity(tableName = "course_data_table")
data class Course (

    @PrimaryKey(autoGenerate = true)
    var key: Int,

    @ColumnInfo(name = "course_code")
    var code : String,

    /*@ColumnInfo(name = "course_name")
    var name : String,

    @ColumnInfo(name = "course_time")
    var time : Time,

    @ColumnInfo(name = "course_days")
    var days : Array<DayOfWeek>*/


)