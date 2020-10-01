package com.example.timetablebphc.ui.addCourse

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timetablebphc.AlarmReceiver
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.CourseRoomDatabase
import com.example.timetablebphc.courseDB.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.*

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CourseRepository


    val context = application

    private val allCourses: LiveData<List<Course>>


    init {
        val courseDao = CourseRoomDatabase.getDatabase(application, viewModelScope).courseDao()
        repository = CourseRepository(courseDao)
        allCourses = repository.allCourses
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCourse(course)
        if (course.notify)
            setNotificationForCourse(course)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationForCourse(course: Course) {
        val remindBefore = 10
        val totalMinutes = (course.time.hour * 60 + course.time.minute) - remindBefore
        val hour = totalMinutes / 60
        val minutes = totalMinutes % 60
        val courseCode =
            course.code //Displays in notification, might want to change to course detail later

        for (i in 0..5) {
            if (course.days[i])
                setNotification(courseCode, i, hour, minutes)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotification(courseCode: String, dayNumber: Int, hour: Int, minutes: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

        val notificationIntent = Intent(context, AlarmReceiver::class.java)
        notificationIntent.putExtra("title", courseCode)
        notificationIntent.putExtra("message", "You have a class!")
        val broadcast = PendingIntent.getBroadcast(
            context,
            100,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val cal = Calendar.getInstance()
        cal[Calendar.DAY_OF_WEEK] = dayNumber
        cal[Calendar.HOUR_OF_DAY] = hour
        cal[Calendar.MINUTE] = minutes

        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            cal.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            broadcast
        )
    }
}