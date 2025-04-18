package com.cronparser

import com.cronparser.factories.CronFieldFactory

const val CRON_EXPRESSION_MAX_LENGTH = 6
val INDEX_TO_FIELD_MAPPING = mapOf(
    0 to CronField.MINUTES,
    1 to CronField.HOURS,
    2 to CronField.DAY_OF_MONTH,
    3 to CronField.MONTH,
    4 to CronField.DAY_OF_WEEK,
)

class CronParser(private val responseBuilder: ResponseBuilder) {
    
    fun parse(cronExpression: String): Map<String, Any> {
        val parts = cronExpression.split(" ")
        
        if (parts.size != CRON_EXPRESSION_MAX_LENGTH) {
            throw InvalidCronStringException("Expected [minute] [hour] [dayOfMonth] [month] [dayOfWeek] [command] but got: $cronExpression")
        }
        val entries = mutableListOf<ResponseEntry>()
        for (index in 0..parts.size-2) {
            val field = INDEX_TO_FIELD_MAPPING.get(index)
            if (field == null) {
                logger.error {"Index: $index is not mapped in $INDEX_TO_FIELD_MAPPING"}
                throw Exception("Some Error while processing cron expression")
            }
            val cronField = field.name
            val output = CronFieldFactory().getFieldHandler(cronField).parse(parts[index])
            entries.add(ResponseEntry(cronField, output.map {it.toString()}))
        }
        val command = parts.subList(CRON_EXPRESSION_MAX_LENGTH-1, parts.size).joinToString(" ")
        entries.add(
            ResponseEntry("command", listOf(command))
        )
        
        return responseBuilder.build(entries)
    }
}