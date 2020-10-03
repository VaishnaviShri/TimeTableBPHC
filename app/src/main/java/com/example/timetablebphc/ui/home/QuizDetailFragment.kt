package com.example.timetablebphc.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.timetablebphc.R
import com.example.timetablebphc.ui.dashboard.TimeTableViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_course_details.*
import kotlinx.android.synthetic.main.fragment_quiz_details.*

@AndroidEntryPoint
class QuizDetailFragment :Fragment(){

    private val homeViewModel: HomeViewModel by viewModels()

    private var position :Int = 0
    private val args: QuizDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        position = args.position.toInt()
        return inflater.inflate(R.layout.fragment_quiz_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.allQuizzes.observe(viewLifecycleOwner, Observer { quizzes ->
            quizzes?.let {
                if(it.isNotEmpty()) {
                    val quiz = it[position]
                    quiz_type.text = quiz.type
                    course_name.text = quiz.course
                    quiz_date.text = quiz.date.toString()
                    quiz_time.text = quiz.time.toString()
                    delete_quiz_button.setOnClickListener {
                        homeViewModel.deleteQuiz(quiz)
                        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_quiz_detail_to_home)
                    }
                }
            }
        })
    }
}