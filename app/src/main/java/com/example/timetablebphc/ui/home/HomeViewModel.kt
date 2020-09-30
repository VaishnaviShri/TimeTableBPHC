package com.example.timetablebphc.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.CourseRoomDatabase
import com.example.timetablebphc.courseDB.Quiz
import com.example.timetablebphc.ui.dashboard.TimeTableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HomeRepository

    val allQuizzes: LiveData<List<Quiz>>

    init {
        val homeDao = CourseRoomDatabase.getDatabase(application, viewModelScope).homeDao()
        repository = HomeRepository(homeDao)
        allQuizzes = repository.allQuizzes
    }


    fun deleteQuiz(quiz: Quiz) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteQuiz(quiz)
    }
}