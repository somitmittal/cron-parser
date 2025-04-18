package com.cronparser.handlers

import com.cronparser.AbstractHandler

class HourHandler : AbstractHandler() {
    override val min: Int = 0
    override val max: Int = 23
}