package com.example.timetablebphc.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.Quiz
import com.example.timetablebphc.dao.TimeTableDao
import javax.inject.Inject

class TimeTableRepository @Inject constructor(private val timeTableDao: TimeTableDao) {

    val allCourses: LiveData<List<Course>> = timeTableDao.getAllCourses()
    val allQuizzes: LiveData<List<Quiz>> = timeTableDao.getAllQuizzes()

    @WorkerThread
    suspend fun insertQuiz(quiz: Quiz) {
        timeTableDao.insertQuiz(quiz)
    }

    @WorkerThread
    suspend fun insertCourse(course: Course) {
        timeTableDao.insertCourse(course)
    }

    @WorkerThread
    suspend fun deleteQuiz(quiz: Quiz) {
        timeTableDao.deleteQuiz(quiz)
    }

    @WorkerThread
    suspend fun deleteCourse(course: Course) {
        timeTableDao.deleteCourse(course)
    }

}