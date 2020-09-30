package com.example.timetablebphc.ui.dashboard

import androidx.lifecycle.LiveData
import com.example.timetablebphc.courseDB.Course

class TimeTableRepository(private val timeTableDao: TimeTableDao) {

    val allCourses: LiveData<List<Course>> = timeTableDao.getAllCourses()

   /* @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCourse(course: Course) {
        courseDao.insertCourse(course)
    }

    suspend fun insertQuiz(quiz: Quiz) {
        courseDao.insertQuiz(quiz)
    }*/

    suspend fun deleteCourse(course: Course) {
        timeTableDao.delete(course)
    }
}