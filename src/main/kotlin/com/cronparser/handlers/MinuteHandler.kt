package com.cronparser.handlers

import com.cronparser.AbstractHandler

class MinuteHandler : AbstractHandler() {
    override val min: Int = 0
    override val max: Int = 59
}