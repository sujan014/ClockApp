package com.example.clockapp.presentation.state


enum class Period{
    AM,
    PM,
}

data class SwTime(
    var hour: Int = 0,
    var min: Int = 0,
    var sec: Int = 0,
    var millis10: Int = 0,
    var period: Period = Period.AM
)

