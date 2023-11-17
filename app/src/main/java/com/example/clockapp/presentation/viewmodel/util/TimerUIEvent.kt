package com.example.clockapp.presentation.viewmodel.util

sealed class TimerUIEvent{
    data class ShowSnackbar(val message: String): TimerUIEvent()
    object TimerComplete: TimerUIEvent()
    object MainTab: TimerUIEvent()
}
