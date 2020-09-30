package com.example.timetablebphc.ui.addUI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.CourseRoomDatabase
import com.example.timetablebphc.courseDB.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CourseRepository



    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCourses: LiveData<List<Course>>
    val allQuizzes : LiveData<List<Quiz>>


    init {
        val courseDao = CourseRoomDatabase.getDatabase(application, viewModelScope).courseDao()
        repository = CourseRepository(courseDao)
        allCourses = repository.allCourses
        allQuizzes = repository.allQuizzes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCourse(course)
    }

    fun insertQuiz(quiz: Quiz) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertQuiz(quiz)
    }

    //TODO: add the following code to TimeTableViewModel




}
