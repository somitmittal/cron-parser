package com.cronparser.handlers

import com.cronparser.AbstractHandler
import com.cronparser.InvalidCronStringException

class DayOfWeekHandler : AbstractHandler() {
    override val min: Int = 1
    override val max: Int = 7
    
    private val dayNames = mapOf(
        1 to "MON", 2 to "TUE", 3 to "WED", 4 to "THU", 5 to "FRI", 6 to "SAT", 7 to "SUN"
    )
    
    override fun representation(values: List<Int>): List<String> {
        return values.map { it.toString() }
    }

    override fun parseRange(field: String): List<Int> {
        val parts = field.split("-")
        if (parts.size != 2) {
            throw InvalidCronStringException("Invalid range format: $field")
        }

        var start = parts[0].toIntOrNull() ?:
        throw InvalidCronStringException("Invalid range start: ${parts[0]}")
        val end = parts[1].toIntOrNull() ?:
        throw InvalidCronStringException("Invalid range end: ${parts[1]}")

        if (start < min || end > max) {
            throw InvalidCronStringException("Range values out of bounds: $field")
        }

        val ans = mutableListOf<Int>()
        if (start > end) {
         while(start<=max) {
             ans.add(start)
             start+=1;
         }
            start = end
            while(start<=end) {
                ans.add(start)
                start+=1
            }
            ans.sort()
            return ans
        }

        return (start..end).toList()
    }
}