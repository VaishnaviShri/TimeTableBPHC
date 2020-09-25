package com.example.timetablebphc


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDao {

    @Insert
    suspend fun insert(course: Course) : Long

    @Update
    suspend fun update(course: Course) : Int

    @Delete
    suspend fun delete(course: Course) : Int

    @Query("DELETE FROM course_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM course_data_table")
    fun getAll():LiveData<List<Course>>
}