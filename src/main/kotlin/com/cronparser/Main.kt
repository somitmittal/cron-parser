package com.cronparser

import mu.KotlinLogging
val logger = KotlinLogging.logger {}

class CronParserMain {

    fun parseArgs(cronExpression: String): String {
        return try {
            val result = CronParser(TableResponseBuilder()).parse(cronExpression)
            formatOutput(result)
        } catch (e: InvalidCronStringException) {
            logger.error {"Error: $e, Invalid CronExpression: $cronExpression"}
            throw e
        }
    }
    
    private fun formatOutput(result: Map<String, Any>): String {
        val sb = StringBuilder()
        
        sb.append("minute ${(result["minute"] as List<*>).joinToString(" ")}\n")
        sb.append("hour ${(result["hour"] as List<*>).joinToString(" ")}\n")
        sb.append("day of month ${(result["day_of_month"] as List<*>).joinToString(" ")}\n")
        sb.append("month ${(result["month"] as List<*>).joinToString(" ")}\n")
        sb.append("day of week ${(result["day_of_week"] as List<*>).joinToString(" ")}\n")
        sb.append("command ${(result["command"] as List<*>).first()}\n")
        
        return sb.toString()
    }
}

fun main(args: Array<String>) {
    val cronExpression = args.joinToString(" ")
    logger.info {"Cron Expression: $cronExpression"}
    val parser = CronParserMain()
    println(parser.parseArgs(cronExpression))
}