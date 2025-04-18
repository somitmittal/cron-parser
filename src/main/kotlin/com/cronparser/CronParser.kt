package com.cronparser

import com.cronparser.handlers.*

class CronParser(private val responseBuilder: ResponseBuilder) {
    
    fun parse(cronExpression: String): Map<String, Any> {
        val parts = cronExpression.split(" ")
        
        if (parts.size < 6) {
            throw InvalidCronStringException("Expected [minute] [hour] [dayOfMonth] [dayOfWeek] [command] but got: $cronExpression")
        }
        
        val minute = MinuteHandler().parse(parts[0])
        val hour = HourHandler().parse(parts[1])
        val dayOfMonth = DayOfMonthHandler().parse(parts[2])
        val month = MonthHandler().parse(parts[3])
        val dayOfWeek = DayOfWeekHandler().parse(parts[4])
        val command = parts.subList(5, parts.size).joinToString(" ")
        
        val entries = listOf(
            TableResponseEntry("minute", minute.map { it.toString() }),
            TableResponseEntry("hour", hour.map { it.toString() }),
            TableResponseEntry("day_of_month", dayOfMonth.map { it.toString() }),
            TableResponseEntry("month", MonthHandler().representation(month)),
            TableResponseEntry("day_of_week", DayOfWeekHandler().representation(dayOfWeek)),
            TableResponseEntry("command", listOf(command))
        )
        
        return responseBuilder.build(entries)
    }
}