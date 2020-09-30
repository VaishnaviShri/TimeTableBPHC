package com.example.timetablebphc.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.timetablebphc.R
import com.example.timetablebphc.courseDB.Course
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.time.LocalTime


class DashboardFragment : Fragment(), CourseListAdapter.CellClickListener {
    private lateinit var timeTableViewModel: TimeTableViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timeTableViewModel =
                ViewModelProviders.of(this).get(TimeTableViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = context?.let { CourseListAdapter(it, this) }
        recyclerview.adapter = adapter
        //recyclerview.layoutManager = GridLayoutManager(context, 6)
        recyclerview.layoutManager = GridLayoutManager(context, 10, GridLayoutManager.HORIZONTAL, false)
        //recyclerView.setLayoutManager(GridLayoutManager(this, numberOfColumns))

    


        Log.v("all courses", timeTableViewModel.allCourses.toString())
        timeTableViewModel.allCourses.observe(viewLifecycleOwner, { courses ->
            courses?.let {
                adapter?.setCourses(getDisplayCourseList(it))
            }
        })
    }
    //TODO : remove this function from UI thread
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDisplayCourseList(courses : List<Course>) : List<Course>{
        //var hour =1
        //var day = 0
        if(courses.isEmpty())
            return courses

        var posCourses =0
        var posDisplayCourses = 0
        val displayCourses = emptyList<Course>().toMutableList()

        val emptyCourse =Course(0,"","", LocalTime.now(), mutableListOf(false), "")
        for(i in 1..60)
            displayCourses.add(emptyCourse)

        for (day in 0 until 6){
            for (hour in 0..9){

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCellClickListener(course: Course) {

        //courseDetailViewModel.setCourse(course)
        //timeTableViewModel.setCurrentCourse(course)
            //view?.findNavController()?.navigate(R.id.startMyFragment)
        //NavHostFragment.findNavController(this).navigate(R.id.action_navigation_dashboard_to_course_detail)
        Toast.makeText(context, course.time.toString(), Toast.LENGTH_LONG).show()
    }

}