package com.cronparser.handlers

import com.cronparser.AbstractHandler

class YearHandler : AbstractHandler() {
    override val min: Int = 2025
    override val max: Int = 2050
}