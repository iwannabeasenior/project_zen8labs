package com.example.marsphotos.helper

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Utils {
    companion object {
        fun dayOfWeek(date: String): String {
//            var year = date.substring(0, 4).toInt()
//            var month = date.substring(5,7).toInt()
//            var day = date.substring(8, 10).toInt()
//            var a: Int = (14-month)/12
//            var y: Int = year - a
//            var m: Int = month + 12*a - 2
//            var result = (day + y + y/4 - y/100 + y/400 + (31*m)/12)%7
//            return when (result) {
//                0 -> "Monday"
//                1 -> "Tuesday"
//                2 -> "Wednesday"
//                3 -> "Thursday"
//                4 -> "Friday"
//                5 -> "Saturday"
//                6 -> "Sunday"
//                else -> "Monday"
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(date, formatter)
            return when(date.dayOfWeek.value) {
                1 -> "Mon"
                2 -> "Tue"
                3 -> "Wed"
                4 -> "Thu"
                5 -> "Fri"
                6 -> "Sat"
                7 -> "Sun"
                else -> "Mon"
            }
        }
    }
}