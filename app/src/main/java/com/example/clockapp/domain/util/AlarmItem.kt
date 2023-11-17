package com.example.clockapp.domain.util

import java.time.LocalDateTime

// Contains fields we need to send to our AlarmManager
data class AlarmItem(
    val time: Long,
    val message: String = "Alarm alert!"
)