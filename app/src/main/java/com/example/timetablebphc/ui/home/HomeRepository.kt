package com.example.timetablebphc.ui.home

import androidx.lifecycle.LiveData
import com.example.timetablebphc.courseDB.Quiz

class HomeRepository(private val homeDao: HomeDao) {

    val allQuizzes: LiveData<List<Quiz>> = homeDao.getAllQuizzes()

    suspend fun deleteQuiz(quiz: Quiz) {
        homeDao.delete(quiz)
    }
}