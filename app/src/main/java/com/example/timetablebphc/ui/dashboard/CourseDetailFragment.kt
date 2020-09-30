package com.example.timetablebphc.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.timetablebphc.R
import com.example.timetablebphc.courseDB.Course
import kotlinx.android.synthetic.main.fragment_course_details.*
import java.time.LocalTime

class CourseDetailFragment : Fragment() {

    private lateinit var timeTableViewModel: TimeTableViewModel

    private var position :Int = 0
    private val args: CourseDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timeTableViewModel = ViewModelProvider(this).get(TimeTableViewModel::class.java)


         position = args.position


        return inflater.inflate(R.layout.fragment_course_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timeTableViewModel.allCourses.observe(viewLifecycleOwner, { courses ->
            courses?.let {
                val course = getDisplayCourseList(it)[position]
                course_code.text = course.code
                course_detail.text = course.detail
                course_time.text = course.time.toString()
                meet_link.text = course.link
                delete_course_button.setOnClickListener {
                    timeTableViewModel.deleteCourse(course)
                    NavHostFragment.findNavController(this).navigate(R.id.action_navigation_course_detail_to_dashboard)
                }
            }
        })
    }

    //DRY
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDisplayCourseList(courses : List<Course>) : List<Course>{
        //var hour =1
        //var day = 0
        if(courses.isEmpty())
            return courses

        var posCourses =0
        var posDisplayCourses = 0
        val displayCourses = emptyList<Course>().toMutableList()

        val emptyCourse = Course(0,"","", LocalTime.now(), mutableListOf(false), "")
        for(i in 1..60)
            displayCourses.add(emptyCourse)

        for (day in 0 until 6){
            for (hour in 0..9){
                //val course = courses[posCourses]

                if(hour == 0) {
                    posDisplayCourses++
                    continue
                }

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
        return  displayCourses
    }
}