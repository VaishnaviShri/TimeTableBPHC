package com.example.timetablebphc.ui.addUI

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.Quiz

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class CourseRepository(private val courseDao: CourseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allCourses: LiveData<List<Course>> = courseDao.getAllCourses()
    val allQuizzes: LiveData<List<Quiz>> = courseDao.getAllQuizzes()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCourse(course: Course) {
        courseDao.insertCourse(course)
    }

    suspend fun insertQuiz(quiz: Quiz) {
        courseDao.insertQuiz(quiz)
    }
}