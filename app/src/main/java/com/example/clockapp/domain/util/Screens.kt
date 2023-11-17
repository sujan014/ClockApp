package com.example.clockapp.domain.util

sealed class Screens(val route: String){
    object Clock: Screens(route = "Clock")
    object Timer: Screens(route = "Timer")
    object Stopwatch: Screens(route = "Stopwatch")
    object Alarm: Screens(route = "Alarm")
}

