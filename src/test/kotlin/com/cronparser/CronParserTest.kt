package com.cronparser

import com.cronparser.handlers.DayOfWeekHandler
import com.cronparser.handlers.MonthHandler
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CronParserTest {
    
    @Test
    fun `test large cron command`() {
        val command = "/bin/echo " + "hello-".repeat(100)
        val facade = CronParser(TableResponseBuilder())
        val result = facade.parse("* * * * * $command")
        
        assertEquals(command, (result["command"] as List<*>).first())
    }
    
    @Test
    fun `test boundary values`() {
        val parser = CronParser(TableResponseBuilder())
        val result = parser.parse("59 23 31 12 7 /usr/bin/test")
        
        assertEquals(listOf("59"), result["minute"])
        assertEquals(listOf("23"), result["hour"])
        assertEquals(listOf("31"), result["day_of_month"])
        assertEquals(MonthHandler().representation(listOf(12)), result["month"])
        assertEquals(DayOfWeekHandler().representation(listOf(7)), result["day_of_week"])
    }
    
    @Test
    fun `test valid cron expression`() {
        val facade = CronParser(TableResponseBuilder())
        val result = facade.parse("*/15 0 1,15 * 1-5 /usr/bin/find")
        
        assertEquals(listOf("0", "15", "30", "45"), result["minute"])
        assertEquals(listOf("0"), result["hour"])
        assertEquals(listOf("1", "15"), result["day_of_month"])
        assertEquals(MonthHandler().representation((1..12).toList()), result["month"])
        assertEquals(DayOfWeekHandler().representation((1..5).toList()), result["day_of_week"])
        assertEquals("/usr/bin/find", (result["command"] as List<*>).first())
    }
    
    @Test
    fun `test invalid day of week range`() {
        val facade = CronParser(TableResponseBuilder())
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/15 0 * * 4-1 invalid-field")
        }
    }
    
    @Test
    fun `test invalid step arguments`() {
        val facade = CronParser(TableResponseBuilder())
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/0 0 * * 1-4 invalid-step=0")
        }
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/10/10 0 * * 1-4 has too many intervals")
        }
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/A 0 * * 1-4 invalid-number")
        }
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("A/A 0 * * 1-4 invalid-number")
        }
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("0/0 0 * * 1-4 invalid-number")
        }
    }
    
    @Test
    fun `test invalid interval arguments`() {
        val facade = CronParser(TableResponseBuilder())
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/15 0 0-2 * 1-3 invalid-dayOfMonth start")
        }
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/10 0 0-2-3 * 1-3 has too many intervals")
        }
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/10 0 1-33 * 1-3 invalid end > 31")
        }
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/10 0 2-1 * 1-2 invalid-number")
        }
    }
    
    @Test
    fun `test invalid chars`() {
        val facade = CronParser(TableResponseBuilder())
        
        assertFailsWith<InvalidCronStringException> {
            facade.parse("*/15 0 0 * 1-3 invalid-dayOfMonth start")
        }
    }
}