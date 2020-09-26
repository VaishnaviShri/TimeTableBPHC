package com.example.timetablebphc.ui.addUI

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.timetablebphc.R
import com.example.timetablebphc.quizDB.Quiz
import kotlinx.android.synthetic.main.fragment_add_course.*
import kotlinx.android.synthetic.main.fragment_add_test.*
import kotlinx.android.synthetic.main.fragment_add_test.button_save
import java.text.SimpleDateFormat
import java.util.*

class AddTestFragment : Fragment() {

    override fun getContext(): Context {
        return super.requireContext()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quizTypes = resources.getStringArray(R.array.quiz_types)
        var quizType = quizTypes[1]
        // access the spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item, quizTypes
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    quizType = quizTypes[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        var courseName = edit_course_name.text.toString()
        edit_course_name.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(arg0: Editable) {
                courseName = edit_word.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        var quizDate = Date(0)

        date.text = SimpleDateFormat("dd/MM").format(System.currentTimeMillis())
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM"
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            date.text = sdf.format(cal.time)
            quizDate = cal.time

        }

        date.setOnClickListener {
            DatePickerDialog(context, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        var quizTime = "00:00"
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            quizTime = "$hour:$minute"
        }


        button_save.setOnClickListener {
            val quiz = Quiz(0, quizType, courseName, quizDate,  quizTime)
        }


    }

    fun getTime(){
        var time = "03:00"
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            //var hour = hour
            var am_pm = ""
            time = "$hour:$minute"
            // AM_PM decider logic
            /*when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"

            }*/
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun getDate(){

        date.text = SimpleDateFormat("dd/MM").format(System.currentTimeMillis())

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM"
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            date.text = sdf.format(cal.time)

        }

        date.setOnClickListener {
            DatePickerDialog(context, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun getQuizType(): String {
        val quizTypes = resources.getStringArray(R.array.quiz_types)
        var type = quizTypes[1]
        // access the spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item, quizTypes
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    type = quizTypes[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        return type
    }
}