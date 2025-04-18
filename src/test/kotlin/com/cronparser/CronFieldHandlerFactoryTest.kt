package com.cronparser

import com.cronparser.factories.CronFieldFactory
import com.cronparser.handlers.*
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertFailsWith

class CronFieldHandlerFactoryTest {
    
    @Test
    fun `test valid field handlers`() {
        val factory = CronFieldFactory()
        
        assertIs<MinuteHandler>(factory.getFieldHandler(CronField.MINUTES.name))
        assertIs<HourHandler>(factory.getFieldHandler(CronField.HOURS.name))
        assertIs<DayOfMonthHandler>(factory.getFieldHandler(CronField.DAY_OF_MONTH.name))
        assertIs<MonthHandler>(factory.getFieldHandler(CronField.MONTH.name))
        assertIs<DayOfWeekHandler>(factory.getFieldHandler(CronField.DAY_OF_WEEK.name))
    }
    
    @Test
    fun `test invalid field handler`() {
        val factory = CronFieldFactory()
        
        assertFailsWith<InvalidCronStringException> {
            factory.getFieldHandler("invalid_field_type")
        }
    }
}