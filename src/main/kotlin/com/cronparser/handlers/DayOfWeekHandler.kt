package com.cronparser.handlers

import com.cronparser.AbstractHandler

class DayOfWeekHandler : AbstractHandler() {
    override val min: Int = 0
    override val max: Int = 7
    
    private val dayNames = mapOf(
        0 to "SUN", 1 to "MON", 2 to "TUE", 3 to "WED", 4 to "THU", 5 to "FRI", 6 to "SAT", 7 to "SUN"
    )
    
    override fun representation(values: List<Int>): List<String> {
        return values.map { it.toString() }
    }
}