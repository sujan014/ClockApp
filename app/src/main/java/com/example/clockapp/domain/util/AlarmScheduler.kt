package com.example.alarmmanager

import com.example.clockapp.domain.util.AlarmItem

interface AlarmScheduler {

    fun schedule(item: AlarmItem)

    fun cancel(item: AlarmItem)
}