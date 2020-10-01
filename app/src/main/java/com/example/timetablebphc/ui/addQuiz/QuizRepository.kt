package com.example.timetablebphc.ui.addQuiz

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.timetablebphc.courseDB.Quiz


class QuizRepository(private val quizDao: QuizDao) {

    val allQuizzes: LiveData<List<Quiz>> = quizDao.getAllQuizzes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertQuiz(quiz: Quiz) {
        quizDao.insertQuiz(quiz)
    }
}