package com.cronparser

import com.cronparser.handlers.*

const val CRON_EXPRESSION_MAX_LENGTH = 6

class CronParser(private val responseBuilder: ResponseBuilder) {
    
    fun parse(cronExpression: String): Map<String, Any> {
        val parts = cronExpression.split(" ")
        
        if (parts.size < CRON_EXPRESSION_MAX_LENGTH) {
            throw InvalidCronStringException("Expected [minute] [hour] [dayOfMonth] [dayOfWeek] [command] but got: $cronExpression")
        }
        
        val minute = MinuteHandler().parse(parts[0])
        val hour = HourHandler().parse(parts[1])
        val dayOfMonth = DayOfMonthHandler().parse(parts[2])
        val month = MonthHandler().parse(parts[3])
        val dayOfWeek = DayOfWeekHandler().parse(parts[4])
        val command = parts.subList(5, parts.size).joinToString(" ")
        
        val entries = listOf(
            ResponseEntry("minute", minute.map { it.toString() }),
            ResponseEntry("hour", hour.map { it.toString() }),
            ResponseEntry("day_of_month", dayOfMonth.map { it.toString() }),
            ResponseEntry("month", MonthHandler().representation(month)),
            ResponseEntry("day_of_week", DayOfWeekHandler().representation(dayOfWeek)),
            ResponseEntry("command", listOf(command))
        )
        
        return responseBuilder.build(entries)
    }
}