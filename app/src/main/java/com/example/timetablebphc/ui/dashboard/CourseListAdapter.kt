/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.timetablebphc.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.MenuView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.timetablebphc.R
import com.example.timetablebphc.courseDB.Course


class CourseListAdapter internal constructor(
        context: Context,
) : RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var courses = emptyList<Course>() // Cached copy of words

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.cell_text_view)
        val cellTime: TextView = itemView.findViewById(R.id.cell_time)
        val courseCardView: CardView = itemView.findViewById(R.id.course_card_view)
        val courseCardContent: LinearLayout = itemView.findViewById(R.id.course_card_content)
        val headerCardView: CardView = itemView.findViewById(R.id.header_card_view)
        val headerTextView: TextView = itemView.findViewById(R.id.header_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = inflater.inflate(R.layout.row_course, parent, false)
        parent.context
        return CourseViewHolder(itemView)
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        holder.setIsRecyclable(false)

        val current = courses[position]

        val a = listOf("MON","TUE","WED","THU","FRI","SAT")

        if(position%10 == 0){//day header
            holder.courseCardView.visibility = GONE
            holder.headerCardView.visibility = VISIBLE
            holder.headerCardView.isClickable = true
            holder.headerTextView.text = a[position/10]
        }else{
            //holder.textView.text = position.toString()
            if(current.code == "") {
                //holder.courseCardView.visibility = INVISIBLE
                holder.courseCardView.setCardBackgroundColor(R.color.empty_cell_color)
            }
            else {
                holder.courseCardView.isClickable = false
                holder.textView.text = current.code
                val hr = current.time.hour -7
                val s = "$hr:00"
                holder.cellTime.text = s //TODO: Display time (am/pm)
            }
        }

        holder.itemView.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavigationDashboardToCourseDetail(position)
            holder.itemView.findNavController().navigate(action)
        }
    }

    internal fun setCourses(courses: List<Course>) {
        this.courses = courses
        Log.v("all courses list", courses.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount() = courses.size
}


