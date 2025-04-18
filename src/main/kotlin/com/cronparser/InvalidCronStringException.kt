package com.cronparser

class InvalidCronStringException(val msg: String) : Exception(msg) {
    override fun toString(): String {
        return "InvalidCronExpressionException: $msg"
    }
}