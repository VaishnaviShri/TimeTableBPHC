package com.example.timetablebphc.ui.addQuiz

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timetablebphc.AlarmReceiver
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.courseDB.CourseRoomDatabase
import com.example.timetablebphc.courseDB.Quiz
import com.example.timetablebphc.ui.addCourse.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuizRepository

    val context = application

    private val allQuizzes : LiveData<List<Quiz>>


    init {
        val quizDao = CourseRoomDatabase.getDatabase(application, viewModelScope).quizDao()
        repository = QuizRepository(quizDao)
        allQuizzes = repository.allQuizzes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertQuiz(quiz: Quiz) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertQuiz(quiz)
        setNotification(quiz)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotification(quiz: Quiz) {
        val remindBefore = 10 //notification 10 minutes before the quiz
        val totalMinutes = (quiz.time.hour * 60 + quiz.time.minute) - remindBefore
        val hour = totalMinutes / 60
        val minutes = totalMinutes % 60
        val title = quiz.type + " : " + quiz.course//Displays in notification, might want to change to course detail later

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

        val notificationIntent = Intent(context, AlarmReceiver::class.java)
        notificationIntent.putExtra("title", title)
        notificationIntent.putExtra("message", "Coming up.")
        val broadcast = PendingIntent.getBroadcast(
            context,
            100,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = quiz.date.year
        cal[Calendar.DAY_OF_YEAR] = quiz.date.dayOfYear
        cal[Calendar.HOUR_OF_DAY] = hour
        cal[Calendar.MINUTE] = minutes


        alarmManager!!.setExact(
            AlarmManager.RTC_WAKEUP,
            cal.timeInMillis,
            broadcast
        )
    }

}