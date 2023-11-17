package com.example.clockapp.presentation.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.clockapp.domain.util.AndroidAlarmScheduler
import com.example.clockapp.domain.util.AlarmItem
import com.example.clockapp.presentation.state.Period
import com.example.clockapp.presentation.state.SwTime
import com.example.clockapp.presentation.viewmodel.util.AlarmUIEvent
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class AlarmVM: ViewModel() {

    var alarmTime by mutableStateOf<SwTime>(SwTime())

    var alarmHour by mutableStateOf(0)
    var alarmMinute by mutableStateOf(0)
    var alarmSec by mutableStateOf(0)
    var alarmPeriod by mutableStateOf(Period.AM)

    var alarmItem: AlarmItem? = null

    private lateinit var scheduler: AndroidAlarmScheduler
    //var context: Context? = null
    fun setContext(thisContext: Context)
    {
//        context = thisContext
        scheduler = AndroidAlarmScheduler(context = thisContext)
    }

    fun onAlarmEvent(alarmEvent: AlarmUIEvent){
        when(alarmEvent){
            AlarmUIEvent.UIEvent -> { }
            is AlarmUIEvent.OnHourChange -> {
                alarmHour = alarmEvent.hour.toInt()
                alarmTime.hour = alarmHour
            }
            is AlarmUIEvent.OnMinuteChange -> {
                alarmMinute = alarmEvent.minute.toInt()
                alarmTime.min = alarmMinute
            }
            is AlarmUIEvent.OnSecondChange -> {
                alarmSec = alarmEvent.second.toInt()
                alarmTime.sec = alarmSec
            }
            is AlarmUIEvent.OnPeriodChange -> {
                alarmPeriod =
                    if (alarmEvent.period == "AM")
                        Period.AM
                    else
                        Period.PM
                alarmTime.period = alarmPeriod
            }
            is AlarmUIEvent.OnAlarmCancel -> {
                cancelAlarm()
            }
            is AlarmUIEvent.OnAlarmSchedule -> {
                setAlarm()
            }
        }
    }

    private fun cancelAlarm()
    {
        //alarmItem?.let(scheduler::cancel)
        scheduler.cancel(alarmItem!!)
    }

    private fun setAlarm()
    {
        var hrlong = alarmTime.hour*3600    // + ( if (alarmTime.period == Period.PM) 12*3600 else 0)
        var minLong = alarmTime.min*60
        var secLong = alarmTime.sec

        val currentDateTime: java.util.Date = java.util.Date()      // gives time in millis elapsed since 1970:0:0 GMT
        val currentTimestamp: Long = currentDateTime.time

        val nowInstance = Calendar.getInstance()
        val nowEpochSeconds = nowInstance.time
        val nowHour12Format: Int =nowInstance.get(Calendar.HOUR) // return the hour in 24 hrs format (ranging from 0-23)
        val nowMinute: Int = nowInstance.get(Calendar.MINUTE)
        val nowPeriod: Int = nowInstance.get(Calendar.AM_PM)
        var timetoAlarmInMillis: Long = 0

        var epochNow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        if (alarmTime.period.ordinal == nowPeriod){    // && calendarPeriod == 0){ // AM
            if (nowHour12Format > alarmTime.hour){
                // eg: now hour = 4 am, set alarm hour = 2 am
                val temp = 12 * 3600 - (hrlong + minLong + secLong)
                // subtract hour, min, sec from 12*3600.
                timetoAlarmInMillis = epochNow + temp
            } else if (nowHour12Format < alarmTime.hour) {
                // eg: now hour = 2 am, set alarm hour = 4 am
                val temp = hrlong + minLong + secLong
                timetoAlarmInMillis = epochNow + temp
            } else if (nowHour12Format == alarmTime.hour){
                if (nowMinute > alarmTime.min){
                    // now = 45 min, alarm = 44 min
                    var temp = (nowMinute - alarmTime.min) * 60
                    temp = 12*3600 - temp
                    timetoAlarmInMillis = epochNow + temp
                } else {
                    var temp = (alarmTime.min - nowMinute) * 60
                    timetoAlarmInMillis = epochNow + temp
                }
            }
        } else if (alarmTime.period.ordinal != nowPeriod){
            /*  eg: now hour = 4 am, set alarm hour = 2 am
                eg: now hour = 4 pm, set alarm hour = 2 am
             */
            var nowToNoon = (12 - nowHour12Format) * 3600 + (59 - nowMinute) * 60
            var noonToAlarmTime = alarmTime.hour * 3600 + alarmTime.min * 60
            timetoAlarmInMillis = epochNow + nowToNoon + noonToAlarmTime
        }

        Log.d("AlarmLog", "current time: $nowHour12Format $nowPeriod")

        alarmItem = AlarmItem(
            time = timetoAlarmInMillis*1000,
            message = "Alarm set"
        )
        scheduler.schedule(item = alarmItem!!)
    }
}