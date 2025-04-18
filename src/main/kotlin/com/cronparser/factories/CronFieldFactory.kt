package com.cronparser.factories

import com.cronparser.CronField
import com.cronparser.AbstractHandler
import com.cronparser.InvalidCronStringException
import com.cronparser.handlers.*

class CronFieldFactory {
    fun getFieldHandler(fieldType: String): AbstractHandler {
        return try {
            when (CronField.valueOf(fieldType)) {
                CronField.MINUTES -> MinuteHandler()
                CronField.HOURS -> HourHandler()
                CronField.DAY_OF_MONTH -> DayOfMonthHandler()
                CronField.MONTH -> MonthHandler()
                CronField.DAY_OF_WEEK -> DayOfWeekHandler()
            }
        } catch (e: IllegalArgumentException) {
            throw InvalidCronStringException("Invalid field type: $fieldType")
        }
    }
}