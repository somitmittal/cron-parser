package com.cronparser.handlers

import com.cronparser.AbstractHandler

class DayOfMonthHandler : AbstractHandler() {
    override val min: Int = 1
    override val max: Int = 31
}