package com.cronparser

import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainTest {
    
    @Test
    fun `test valid cron expression`() {
        mockkConstructor(CronParser::class)
        every { anyConstructed<CronParser>().parse(any()) } returns mapOf(
            "minute" to listOf("0", "15", "30", "45"),
            "hour" to listOf("0"),
            "day_of_month" to listOf("1", "15"),
            "month" to listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"),
            "day_of_week" to listOf("MON", "TUE", "WED", "THU", "FRI"),
            "command" to listOf("/usr/bin/find")
        )
        
        val result = CronParserMain().parseArgs("*/15 0 1,15 * 1-5 /usr/bin/find")
        assertTrue(result.contains("minute 0 15 30 45"))
    }
    
    @Test
    fun `test invalid cron expression`() {
        mockkConstructor(CronParser::class)
        every { anyConstructed<CronParser>().parse(any()) } throws InvalidCronStringException("Test error")
        
        val result = assertThrows<InvalidCronStringException>{ CronParserMain().parseArgs("1213") }
        assertTrue {
            result.message?.contains("Test error") == true
        }
    }
}