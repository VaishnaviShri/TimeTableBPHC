package com.example.timetablebphc.ui.dashboard

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.timetablebphc.courseDB.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

class TimeTableViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TimeTableRepository

    val allCourses: LiveData<List<Course>>

    init {
        val timeTableDao = CourseRoomDatabase.getDatabase(application, viewModelScope).timeTableDao()
        repository = TimeTableRepository(timeTableDao)
        allCourses = repository.allCourses
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun deleteCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCourse(course)
    }

}
