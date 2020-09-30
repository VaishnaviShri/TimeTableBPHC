package com.example.timetablebphc.ui.addUI

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.timetablebphc.MainActivity
import com.example.timetablebphc.R
import com.example.timetablebphc.courseDB.Course
import kotlinx.android.synthetic.main.fragment_add_course.*
import kotlinx.android.synthetic.main.fragment_add_course.button_save
import java.time.LocalTime


class AddCourseFragment : Fragment() {

    lateinit var daysList: MutableList<Boolean>

    private lateinit var courseViewModel: CourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)
        daysList = mutableListOf(false, false, false, false, false, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_course, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var courseTime = LocalTime.now()
        course_time_spinner.setOnTimeChangedListener { _, hour, minute ->
            courseTime = LocalTime.of(hour, minute)
        }

        for (i in 0 until week_grid_layout.childCount) {
            val dayCheckBox = week_grid_layout.getChildAt(i)
            dayCheckBox.setOnClickListener {
                if (dayCheckBox is CheckBox) {
                    val checked: Boolean = dayCheckBox.isChecked
                    daysList[i] = checked
                }
                //onCheckboxClicked(dayCheckBox) }
            }


            button_save.setOnClickListener {
                val courseCode = edit_course_code.text.trim().toString()
                val courseDetails = edit_course_detail.text.trim().toString()
                val meetLink = edit_meet_link.text.trim().toString()
                val hourInTT = courseTime.hour-7


                val course = Course(0, courseCode, courseDetails, courseTime, daysList, meetLink)
                courseViewModel.insertCourse(course)
                val intent = Intent(context, MainActivity::class.java) // (1) (2)
                startActivity(intent)

            }
        }
    }
}
