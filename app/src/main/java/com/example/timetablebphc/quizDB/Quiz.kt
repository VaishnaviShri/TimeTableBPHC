package com.example.timetablebphc.quizDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time
import java.time.DayOfWeek

@Entity(tableName = "quiz_data_table")
data class Quiz (

    @PrimaryKey(autoGenerate = true)
    var key: Int,

    @ColumnInfo(name = "quiz_type")
    var type : String,

    @ColumnInfo(name = "course_name")
    var course : String,

    @ColumnInfo(name = "quiz_date")
    var date : String,


    @ColumnInfo(name = "quiz_time ")
    var time : String


)