package com.cronparser

import kotlin.test.Test
import kotlin.test.assertEquals

class InvalidCronExpressionExceptionTest {
    
    @Test
    fun `test exception message`() {
        val exception = InvalidCronStringException("Test message")
        assertEquals("InvalidCronStringException: Test message", exception.toString())
    }
}