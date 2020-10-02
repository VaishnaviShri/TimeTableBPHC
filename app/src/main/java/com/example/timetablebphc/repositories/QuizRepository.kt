package com.example.timetablebphc.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.timetablebphc.courseDB.Quiz
import com.example.timetablebphc.dao.QuizDao
import javax.inject.Inject


class QuizRepository @Inject constructor(private val quizDao: QuizDao) {

    val allQuizzes: LiveData<List<Quiz>> = quizDao.getAllQuizzes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertQuiz(quiz: Quiz) {
        quizDao.insertQuiz(quiz)
    }
}