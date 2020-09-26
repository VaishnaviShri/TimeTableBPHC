package com.example.timetablebphc.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timetablebphc.courseDB.CourseViewModel
import com.example.timetablebphc.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
    private lateinit var courseViewModel: CourseViewModel

    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = context?.let { CourseListAdapter(it) }
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(context)

        // Get a new or existing ViewModel from the ViewModelProvider.
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        Log.v("all courses", courseViewModel.allCourses.toString())
        courseViewModel.allCourses.observe(viewLifecycleOwner, Observer { courses ->
            // Update the cached copy of the words in the adapter.
            courses?.let {
                adapter?.setCourses(it)
            }
        })
    }

}