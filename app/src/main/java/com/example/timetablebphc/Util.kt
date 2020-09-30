package com.example.timetablebphc

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.timetablebphc.courseDB.Course
import java.time.LocalTime

class Util {
    object Util {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getDisplayCourseList(courses: List<Course>): List<Course> {
            //var hour =1
            //var day = 0
            if (courses.isEmpty())
                return courses

            var posCourses = 0
            var posDisplayCourses = 0
            val displayCourses = emptyList<Course>().toMutableList()

            val emptyCourse = Course(0, "", "", LocalTime.now(), mutableListOf(false), "")
            for (i in 1..54)
                displayCourses.add(emptyCourse)
            for (day in 0 until 6) {
                for (hour in 1..9) {
                    //val course = courses[posCourses]

                    for (course in courses) {
                        val courseHour = course.time.hour - 7
                        if (course.days[day] && courseHour == hour) {
                            displayCourses[posDisplayCourses] = course
                            //displayCourses.add(posDisplayCourses,course)
                            posCourses++
                        }
                    }
                    posDisplayCourses++
                }
            }
            return displayCourses
        }
    }
}