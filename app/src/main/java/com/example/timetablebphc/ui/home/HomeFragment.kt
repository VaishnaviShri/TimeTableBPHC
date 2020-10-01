package com.example.timetablebphc.ui.home

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
import com.example.timetablebphc.R
import com.example.timetablebphc.ui.addCourse.CourseViewModel
import com.example.timetablebphc.courseDB.Quiz
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var courseViewModel: CourseViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = context?.let { QuizListAdapter(it) }
        recyclerview_home.adapter = adapter
        recyclerview_home.layoutManager = LinearLayoutManager(context)

        Log.v("all quizzes", homeViewModel.allQuizzes.toString())
        homeViewModel.allQuizzes.observe(viewLifecycleOwner, Observer { quizzes ->
            // Update the cached copy of the words in the adapter.
            quizzes?.let {
                adapter?.setQuizzes(rearrangeList(it))
            }
        })
    }
    private fun rearrangeList(quizzes: List<Quiz>): List<Quiz>{
        val comparator = compareBy<Quiz> { it.date }
        return quizzes.sortedWith(comparator.thenBy { it.time })
    }
}