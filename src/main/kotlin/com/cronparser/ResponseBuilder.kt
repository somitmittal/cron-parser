package com.cronparser

interface ResponseBuilder {
    fun build(entries: List<ResponseEntry>): Map<String, Any>
}

class TableResponseBuilder : ResponseBuilder {
    override fun build(entries: List<ResponseEntry>): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        
        entries.forEach { entry ->
            result[entry.name] = entry.values
        }
        
        return result
    }
}