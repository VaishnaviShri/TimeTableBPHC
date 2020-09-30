package com.example.timetablebphc.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timetablebphc.R
import com.example.timetablebphc.courseDB.CourseViewModel
import com.example.timetablebphc.courseDB.Quiz
import com.example.timetablebphc.ui.dashboard.CourseListAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var courseViewModel: CourseViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = context?.let { QuizListAdapter(it) }
        recyclerview_home.adapter = adapter
        recyclerview_home.layoutManager = LinearLayoutManager(context)

        // Get a new or existing ViewModel from the ViewModelProvider.
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        Log.v("all quizzes", courseViewModel.allQuizzes.toString())
        courseViewModel.allQuizzes.observe(viewLifecycleOwner, Observer { quizzes ->
            // Update the cached copy of the words in the adapter.
            quizzes?.let {
                adapter?.setQuizzes(rearrangeList(it))
            }
        })
    }
    private fun rearrangeList(quizzes: List<Quiz>): List<Quiz>{
        val comparator = compareBy<Quiz> { it.date }
        val anotherComparator = comparator.thenBy { it.time }

        return quizzes.sortedWith(anotherComparator)

        //return quizzes.sortedBy { it.date }
        //dates.sortWith(compareBy<Date> { it.year }.thenBy { it.month }

    }
}