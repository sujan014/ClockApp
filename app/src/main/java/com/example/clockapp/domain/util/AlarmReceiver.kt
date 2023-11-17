package com.example.clockapp.domain.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AlarmReceiver: BroadcastReceiver() {

    companion object {
        var alarmMsg by mutableStateOf("Set your alarm")
            private set
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE")?:return
        Log.d("AlarmLog", ":$message")
        alarmMsg = message
    }
}