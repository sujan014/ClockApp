package com.example.clockapp.presentation.viewmodel.util

import android.content.Context
import com.example.clockapp.presentation.state.Period

sealed class AlarmUIEvent{
    object UIEvent: AlarmUIEvent()
    object OnAlarmSchedule : AlarmUIEvent()
    object OnAlarmCancel : AlarmUIEvent()

    data class OnHourChange(val hour : String) : AlarmUIEvent()
    data class OnMinuteChange(val minute : String) : AlarmUIEvent()
    data class OnSecondChange(val second : String) : AlarmUIEvent()
    data class OnPeriodChange(val period : String) : AlarmUIEvent()
}
