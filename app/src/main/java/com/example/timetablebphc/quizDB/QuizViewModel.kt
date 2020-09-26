package com.example.timetablebphc.quizDB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.CourseRepository
import com.example.timetablebphc.courseDB.CourseRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CourseRepository



    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCourses: LiveData<List<Course>>

    init {
        val courseDao = CourseRoomDatabase.getDatabase(application, viewModelScope).courseDao()
        repository = CourseRepository(courseDao)
        allCourses = repository.allCourses
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCourse(course)
    }
}
