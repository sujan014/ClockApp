package com.example.clockapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clockapp.domain.model.SwState
import com.example.clockapp.domain.model.TimerState
import com.example.clockapp.presentation.state.BtnState
import com.example.clockapp.presentation.state.SwTime
import com.example.clockapp.ui.theme.Purple200
import com.example.clockapp.ui.theme.shinyBlue
import com.example.clockapp.ui.theme.yellow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchVM: ViewModel() {
    private var rtcmillis10 = 0
    private var rtcSec = 0
    private var rtcMin = 0
    private var rtcHour = 0

    var swTime by mutableStateOf(SwTime())

    var btnMain by mutableStateOf(BtnState(shinyBlue, "Start"))
    var btnSide by mutableStateOf(BtnState(yellow, "Reset"))

    var swState by mutableStateOf(SwState.RESET)
    var runState by mutableStateOf(TimerState.STOP)

    private var countJob: Job? = null

    private fun onSwAction(){
        Log.d("SWLog", "changed to $swState")
        when(swState){
            SwState.START->{
                btnMain = btnMain.copy(
                    text = "Stop",
                    color = Red
                )
                runState = TimerState.RUN
                Count()
            }
            SwState.STOP->{
                btnMain = btnMain.copy(
                    text = "Resume",
                    color = Purple200
                )
                runState = TimerState.STOP
            }
            SwState.RESUME->{
                // same as start state
                btnMain = btnMain.copy(
                    text = "Stop",
                    color = Red
                )
                runState = TimerState.RUN
                Count()
            }
            SwState.RESET->{
                runState = TimerState.STOP
                rtcSec = 0
                rtcMin = 0
                rtcHour = 0
                swTime = swTime.copy(
                    hour = rtcHour,
                    min = rtcMin,
                    sec = rtcSec,
                )
                btnMain = btnMain.copy (
                    text = "Start",
                    color = shinyBlue
                )
            }
        }
    }

    fun onBtnMain(){
        Log.d("SWLog", "$swState")
        when (swState) {
            SwState.RESET -> {
                swState = SwState.START
            }
            SwState.START -> {
                swState = SwState.STOP
            }
            SwState.STOP -> {
                swState = SwState.RESUME
            }
            SwState.RESUME->{
                swState = SwState.STOP
            }
            else->{

            }
        }
        onSwAction()
    }

    fun onBtnSide(){
        Log.d("SWLog", "$swState")
        when (swState) {
            SwState.START -> {
                swState = SwState.RESET
                onSwAction()
            }
            SwState.STOP -> {
                swState = SwState.RESET
                onSwAction()
            }
            SwState.RESUME -> {
                swState = SwState.RESET
                onSwAction()
            }
            SwState.RESET -> {

            }
        }
        /*if (swState != SwState.RESET){
            swState = SwState.RESET
            onSwAction()
        }*/
    }

    private fun Count(){
        countJob?.cancel()
        countJob = viewModelScope.launch {
            while(runState == TimerState.RUN){
                rtcmillis10 = 0
                rtcSec++
                if ( rtcSec >= 60 ){
                    rtcSec = 0
                    rtcMin++
                    if (rtcMin >= 60){
                        rtcHour++
                    }
                }

                swTime = swTime.copy(
                    hour = rtcHour,
                    min = rtcMin,
                    sec = rtcSec,
                )
                delay(1000L)
            }
        }
    }
}