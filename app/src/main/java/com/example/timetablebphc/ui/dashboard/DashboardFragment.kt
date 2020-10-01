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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.timetablebphc.R
import com.example.timetablebphc.courseDB.Course
import com.example.timetablebphc.ui.addCourse.CourseViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.time.LocalTime


class DashboardFragment : Fragment() {
    private lateinit var timeTableViewModel: TimeTableViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timeTableViewModel = ViewModelProvider(this).get(TimeTableViewModel::class.java)
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = context?.let { CourseListAdapter(it) }
        recyclerview.adapter = adapter
        //recyclerview.layoutManager = GridLayoutManager(context, 6)
        recyclerview.layoutManager = GridLayoutManager(context, 10, GridLayoutManager.HORIZONTAL, false)
        //recyclerView.setLayoutManager(GridLayoutManager(this, numberOfColumns))

        Log.v("all courses", timeTableViewModel.allCourses.toString())
        timeTableViewModel.allCourses.observe(viewLifecycleOwner, { courses ->
            courses?.let {
                val displayCourses = timeTableViewModel.getDisplayCourseList(it)
                adapter?.setCourses(displayCourses)
            }
        })
    }
}