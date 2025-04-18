package com.cronparser

abstract class AbstractHandler {
    abstract val min: Int
    abstract val max: Int
    
    open fun parse(field: String): List<Int> {
        return when {
            field == "*" -> (min..max).toList()
            field.contains(",") -> parseList(field)
            field.contains("-") -> parseRange(field)
            field.contains("/") -> parseStep(field)
            else -> parsePlain(field)
        }
    }
    
    open fun representation(values: List<Int>): List<String> {
        return values.map { it.toString() }
    }
    
    private fun parseList(field: String): List<Int> {
        return field.split(",").flatMap { parse(it) }
    }
    
    private fun parseRange(field: String): List<Int> {
        val parts = field.split("-")
        if (parts.size != 2) {
            throw InvalidCronStringException("Invalid range format: $field")
        }
        
        val start = parts[0].toIntOrNull() ?: 
            throw InvalidCronStringException("Invalid range start: ${parts[0]}")
        val end = parts[1].toIntOrNull() ?: 
            throw InvalidCronStringException("Invalid range end: ${parts[1]}")
            
        if (start > end) {
            throw InvalidCronStringException("Range start cannot be greater than end: $field")
        }
        
        if (start < min || end > max) {
            throw InvalidCronStringException("Range values out of bounds: $field")
        }
        
        return (start..end).toList()
    }
    
    private fun parseStep(field: String): List<Int> {
        val parts = field.split("/")
        if (parts.size != 2) {
            throw InvalidCronStringException("Invalid step format: $field")
        }
        
        val range = if (parts[0] == "*") (min..max).toList() else parse(parts[0])
        val step = parts[1].toIntOrNull() ?: 
            throw InvalidCronStringException("Invalid step value: ${parts[1]}")
            
        if (step <= 0) {
            throw InvalidCronStringException("Step value must be positive: $step")
        }
        
        return range.filterIndexed { index, _ -> index % step == 0 }
    }
    
    private fun parsePlain(field: String): List<Int> {
        val value = field.toIntOrNull() ?: 
            throw InvalidCronStringException("Invalid field value: $field")
            
        if (value < min || value > max) {
            throw InvalidCronStringException("Field value out of bounds: $value")
        }
        
        return listOf(value)
    }
}