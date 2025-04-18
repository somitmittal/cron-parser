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
        result.map { sb.append(it.key + " " + (it.value as List<*>).joinToString(" ") + "\n") }
        return sb.toString()
    }
}

fun main(args: Array<String>) {
    val cronExpression = args.joinToString(" ")
    logger.info {"Cron Expression: $cronExpression"}
    val parser = CronParserMain()
    println(parser.parseArgs(cronExpression))
}