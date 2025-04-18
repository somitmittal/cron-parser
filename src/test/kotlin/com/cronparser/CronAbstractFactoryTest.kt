package com.cronparser

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertFailsWith

class CronAbstractFactoryTest {
    
    @Test
    fun `test valid factory`() {
        val handler = CronAbstractFactory.get(CronField.MINUTES.name)
        assertNotNull(handler)
    }
    
    @Test
    fun `test invalid cron field`() {
        assertFailsWith<InvalidCronStringException> {
            CronAbstractFactory.get("InvalidField")
        }
    }
}