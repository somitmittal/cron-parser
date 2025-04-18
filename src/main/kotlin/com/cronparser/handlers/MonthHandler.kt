package com.cronparser.handlers

import com.cronparser.AbstractHandler

class MonthHandler : AbstractHandler() {
    override val min: Int = 1
    override val max: Int = 12
    
    private val monthNames = mapOf(
        1 to "JAN", 2 to "FEB", 3 to "MAR", 4 to "APR", 5 to "MAY", 6 to "JUN",
        7 to "JUL", 8 to "AUG", 9 to "SEP", 10 to "OCT", 11 to "NOV", 12 to "DEC"
    )
    
    override fun representation(values: List<Int>): List<String> {
        return values.map { it.toString() }
    }
}