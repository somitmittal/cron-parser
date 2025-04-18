package com.cronparser

class CronParserMain {
    fun parseArgs(): String {
        val args = parseCommandLineArgs()
        val cronExpression = args.cronExpression
        
        return try {
            val result = CronParser(TableResponseBuilder()).parse(cronExpression)
            formatOutput(result)
        } catch (e: InvalidCronStringException) {
            e.toString()
        }
    }
    
    private fun parseCommandLineArgs(): Args {
        // In a real implementation, this would use a library like kotlinx-cli
        // For simplicity, we'll just return a hardcoded value
        return Args("*/15 0 1,15 * 1-5 /usr/bin/find")
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

data class Args(val cronExpression: String)

fun main(args: Array<String>) {
    val cronExpression = args.firstOrNull() ?: "*/15 0 1,15 * 1-5 /usr/bin/find"
    val parser = CronParserMain()
    println(parser.parseArgs())
}