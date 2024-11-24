package com.example.inventory.utils

import java.util.Calendar

fun isDueToday(taskDueDate: Long): Boolean {
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_YEAR)
    val taskCalendar = Calendar.getInstance().apply { timeInMillis = taskDueDate }
    return today == taskCalendar.get(Calendar.DAY_OF_YEAR)
}
