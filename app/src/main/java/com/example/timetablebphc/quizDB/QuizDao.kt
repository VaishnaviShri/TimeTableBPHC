package com.example.timetablebphc.quizDB


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.timetablebphc.courseDB.Course

@Dao
interface QuizDao {

    @Insert
    suspend fun insert(quiz: Quiz) : Long

    @Update
    suspend fun update(quiz: Quiz) : Int

    @Delete
    suspend fun delete(quiz: Quiz) : Int

    @Query("DELETE FROM quiz_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM quiz_data_table")
    fun getAll():LiveData<List<Course>>
}