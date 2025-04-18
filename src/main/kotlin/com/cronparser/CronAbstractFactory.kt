package com.cronparser

import com.cronparser.factories.CronFieldFactory

object CronAbstractFactory {
    fun get(fieldType: String): AbstractHandler {
        return CronFieldFactory().getFieldHandler(fieldType)
    }
}