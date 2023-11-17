package com.example.clockapp.presentation.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clockapp.domain.model.SwState
import com.example.clockapp.presentation.state.BtnState
import com.example.clockapp.presentation.state.SwTime
import com.example.clockapp.presentation.viewmodel.util.TimerUIEvent
import com.example.clockapp.ui.theme.darkGreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TimerVM: ViewModel() {
    var startState by mutableStateOf(false)
    var timerTime by mutableStateOf(SwTime())
    var rtcSec by mutableStateOf(0)
    var rtcMin by mutableStateOf(0)
    var rtcHour by mutableStateOf(0)

    //private var countDownJob: Job? = null
    //var countState by mutableStateOf(TimerState.STOP)
    var timerSwState by mutableStateOf(SwState.RESET)
    var btnPauseResume by mutableStateOf(BtnState(Color.Red, "Pause"))

    private lateinit var cdTimer: CountDownTimer
    var remainingTime: Long = 0

    private val _timerEvent = Channel<TimerUIEvent>()
    val timerEvent = _timerEvent.receiveAsFlow()

    fun setHour(hour: Int){
        rtcHour = hour
        timerTime = timerTime.copy(
            hour = rtcHour
        )
    }

    fun setMin(min: Int){
        rtcMin = min
        timerTime = timerTime.copy(
            min = rtcMin
        )
    }

    fun setSec(sec: Int){
        rtcSec = sec
        timerTime = timerTime.copy(
            sec = rtcSec
        )
    }

    fun resetTime(){
        rtcHour = 0
        rtcMin = 0
        rtcSec = 0
        timerTime = timerTime.copy(
            hour = rtcHour,
            min = rtcMin,
            sec = rtcSec
        )
    }

    fun onStart(){
        timerSwState = SwState.START
        onSwitchAction()
    }

    fun onDelete(){
        timerSwState = SwState.RESET
        onSwitchAction()
    }

    fun onPauseResume(){
        when(timerSwState){
            SwState.START -> {
                timerSwState = SwState.STOP
                onSwitchAction()
            }
            SwState.STOP -> {
                timerSwState = SwState.RESUME
                onSwitchAction()
            }
            SwState.RESUME -> {
                timerSwState = SwState.STOP
                onSwitchAction()
            }
            else -> {
            }
        }
    }

    fun onSwitchAction(){
        when(timerSwState){
            SwState.START -> {
                //countState = TimerState.RUN
                if (timerTime.hour == 0 && timerTime.min == 0 && timerTime.sec == 0){
                    sendTimerUiEvent(TimerUIEvent.MainTab)
                }
                else {
                    btnPauseResume = btnPauseResume.copy(
                        color = Color.Red,
                        text = "Pause"
                    )
                    //timerCountDown()
                    val temp =
                        (rtcSec.toLong() + rtcMin.toLong() * 60 + rtcHour.toLong() * 3600) * 1000
                    sendTimerUiEvent(TimerUIEvent.ShowSnackbar(
                        message = "Timer set to ${timerTime.hour}:${timerTime.min}:${timerTime.sec}"
                    ))
                    countDownFunction(temp)
                }
            }
            SwState.STOP -> {
                //countState = TimerState.STOP
                if (this::cdTimer.isInitialized)
                    cdTimer.cancel()
                btnPauseResume = btnPauseResume.copy(
                    color = darkGreen,
                    text = "Resume"
                )
            }
            SwState.RESUME -> {
                //countState = TimerState.RUN
                btnPauseResume = btnPauseResume.copy(
                    color = Color.Red,
                    text = "Pause"
                )
                //timerCountDown()
                countDownFunction(remainingTime)
            }
            SwState.RESET -> {
                //countState = TimerState.STOP
                if (this::cdTimer.isInitialized)
                    cdTimer.cancel()
                resetTime()
                remainingTime = 0
            }
        }
    }

    private fun sendTimerUiEvent(timerUIEvent: TimerUIEvent){
        viewModelScope.launch {
            _timerEvent.send(timerUIEvent)
        }
    }

    private fun  sendAlarmEvent()
    {
        viewModelScope.launch {
            _timerEvent.send(TimerUIEvent.TimerComplete)
        }
    }

    private fun countDownFunction(longtime: Long){
        Log.d("TimerLog", "total = $longtime sec")
        cdTimer = object: CountDownTimer(longtime, 1000){

            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                val tTime = millisUntilFinished/1000
                Log.d("TimerLog", "time: $remainingTime")
                rtcHour = (tTime / 3600).toInt()
                rtcSec = (tTime % 3600).toInt()
                rtcMin = rtcSec / 60
                rtcSec %= 60
                timerTime = timerTime.copy(
                    hour = rtcHour,
                    min = rtcMin,
                    sec = rtcSec,
                )
            }

            override fun onFinish() {
                Log.e("TimerLog", "done!");
                // initiate alarm here
                sendAlarmEvent()
            }
        }
        cdTimer.start()
    }

/*private fun timerCountDown(){
        countDownJob?.cancel()
        countDownJob = viewModelScope.launch {
            while(countState == TimerState.RUN){
                if (rtcSec == 0 && rtcMin == 0 && rtcHour == 0){
                    countState = TimerState.STOP
                }
                else{
                    if (rtcSec > 0){
                        rtcSec--
                    } else {
                        if (rtcMin > 0){
                            rtcSec = 59
                            rtcMin--
                        } else {
                            if (rtcHour > 0){
                                rtcHour--
                                rtcMin = 59
                                rtcSec = 59
                            } else {
                                countState = TimerState.STOP
                            }
                        }
                    }
                }
                timerTime = timerTime.copy(
                    hour = rtcHour,
                    min = rtcMin,
                    sec = rtcSec,
                )
                delay(1000L)
            }
        }
    }*/
}