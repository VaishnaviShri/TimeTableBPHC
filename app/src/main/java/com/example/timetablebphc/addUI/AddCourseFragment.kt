package com.example.timetablebphc.addUI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.timetablebphc.Course
import com.example.timetablebphc.CourseViewModel
import com.example.timetablebphc.MainActivity
import com.example.timetablebphc.R
import kotlinx.android.synthetic.main.fragment_add_course.*

class AddCourseFragment : Fragment(){

    private lateinit var courseViewModel: CourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var code = edit_word.text.toString()
        edit_word.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(arg0: Editable) {
                code = edit_word.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
        val course = Course(0, code)
        button_save.setOnClickListener {
            if(code == "")
                Toast.makeText(context, "Fields empty", Toast.LENGTH_LONG).show()
            else {
                courseViewModel.insert(course)
                val intent = Intent(context, MainActivity::class.java) // (1) (2)
                startActivity(intent)
            }
        }
    }
}