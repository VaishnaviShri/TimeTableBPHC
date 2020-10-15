package com.example.timetablebphc.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.Quiz

@Dao
interface TimeTableDao {

    @Insert
    suspend fun insertCourse(course: Course) : Long

    @Insert
    suspend fun insertQuiz(quiz: Quiz) : Long

    @Update
    suspend fun updateCourse(course: Course) : Int

    @Delete
    suspend fun deleteCourse(course: Course) : Int

    @Query("DELETE FROM course_data_table")
    suspend fun deleteAllCourses() : Int

    @Delete
    suspend fun deleteQuiz(quiz: Quiz) : Int

    @Query("SELECT * FROM course_data_table")
    fun getAllCourses(): LiveData<List<Course>>

    @Query("SELECT * FROM quiz_data_table")
    fun getAllQuizzes(): LiveData<List<Quiz>>
}