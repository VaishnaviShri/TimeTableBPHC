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

package com.example.timetablebphc.courseDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.timetablebphc.ui.addUI.CourseDao
import com.example.timetablebphc.ui.dashboard.TimeTableDao
import com.example.timetablebphc.ui.home.HomeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Course::class, Quiz ::class], version = 1)
@TypeConverters(Converters::class)
abstract class CourseRoomDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun homeDao(): HomeDao
    abstract fun timeTableDao(): TimeTableDao

    companion object {
        @Volatile
        private var INSTANCE: CourseRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): CourseRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CourseRoomDatabase::class.java,
                        "course_database"
                )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .addCallback(CourseDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class CourseDatabaseCallback(
                private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.courseDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(courseDao: CourseDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //courseDao.deleteAll()

            /*var course = Course(1,"MATH F111")
            courseDao.insert(course)
            course = Course(2, "CS F111")
            courseDao.insert(course)*/
        }
    }


}
