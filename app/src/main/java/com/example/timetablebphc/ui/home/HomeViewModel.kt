package com.example.timetablebphc.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.timetablebphc.courseDB.Quiz
import com.example.timetablebphc.repositories.TimeTableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val repository: TimeTableRepository
) : ViewModel()  {

    val allQuizzes: LiveData<List<Quiz>> = repository.allQuizzes

    fun deleteQuiz(quiz: Quiz) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteQuiz(quiz)
    }

}