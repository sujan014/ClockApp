package com.example.clockapp.domain.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.alarmmanager.AlarmScheduler

class AndroidAlarmScheduler(
    private val context: Context
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply{
            putExtra("EXTRA_MESSAGE", item.message)
        }
        // allow in low power/idle mode
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.time,
            //change minSdk 26 to remove the error in above line
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        // USE_EXACT_ALARM in manifest
        // USE_EXACT_ALARM does not need users permission so user does not need to grant it. This is used where alarm is the core functionality of the app
        // So Google play may likely decline it and ask you to use SCHEDULE ALARM
        // However for alarm clock or calendar app, USE_EXACT_ALARM is totally fine.
    }

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
    //Add in manifest <receiver android:name=".AlarmReceiver"/>
}