package com.example.clockapp

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clockapp.presentation.components.PickerCall
import com.example.clockapp.presentation.components.rememberPickerState
import com.example.clockapp.presentation.state.Period
import com.example.clockapp.presentation.viewmodel.AlarmVM
import com.example.clockapp.presentation.viewmodel.util.AlarmUIEvent
import com.example.clockapp.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmScreen(
    alarmVM: AlarmVM = viewModel(),
    context: Context = LocalContext.current
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = abbey)
    ) {
        val hoursList = remember { (1..12).map { it.toString() } }
        val hourPickState = rememberPickerState()
        val minutesList = remember { (0..59).map{ it.toString()} }
        //val secondsList = remember { (0..59).map{ it.toString()} }
        val periodList = remember { listOf("AM", "PM", "AM", "PM") }

        /*var text by remember { mutableStateOf("") }
        PickerCall(
            items = hoursList,
            value = text,
            onValueChanged = {
                 text = it
            },
            visibleItemsCount = 3,
            modifier = Modifier.weight(1f),
            textModifier = Modifier.padding(8.dp),
            textStyle = TextStyle(fontSize = 25.sp)
        )*/
        Text(text = "Select time", modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.height((10.dp)))
        Row(
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            /*Picker(
                state = hourPickState,
                items = hoursList,
                visibleItemsCount = 3,
                modifier = Modifier.weight(1f),
                textModifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 25.sp)
            )*/
            PickerCall(
                value = alarmVM.alarmTime.hour.toString(),//alarmHour.toString(),
                onValueChanged = {
                    try {
                        //alarmHour = it.toInt()
                        alarmVM.onAlarmEvent(AlarmUIEvent.OnHourChange(it))
                    }
                    catch (e: Exception){
                        Log.d("AlarmLog", "${e.printStackTrace()}")
                    }
                },
                items = hoursList,
                visibleItemsCount = 3,
                modifier = Modifier.weight(1f),
                textModifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 25.sp)
            )
            PickerCall(
                value = alarmVM.alarmTime.min.toString(),   //alarmMinute.toString(),
                onValueChanged = {
                    try {
                        //alarmHour = it.toInt()
                        alarmVM.onAlarmEvent(AlarmUIEvent.OnMinuteChange(it))
                    }
                    catch (e: Exception){
                        Log.d("AlarmLog", "${e.printStackTrace()}")
                    }
                },
                items = minutesList,
                visibleItemsCount = 3,
                modifier = Modifier.weight(1f),
                textModifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 25.sp)
            )
            /*PickerCall(
                value = alarmVM.alarmTime.sec.toString(),
                onValueChanged = {
                    try {
                        alarmVM.onAlarmEvent(AlarmUIEvent.OnSecondChange(it))
                    }
                    catch (e: Exception){
                        Log.d("AlarmLog", "${e.printStackTrace()}")
                    }
                },
                items = secondsList,
                visibleItemsCount = 3,
                modifier = Modifier.weight(1f),
                textModifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 25.sp)
            )*/
            PickerCall(
                value = if (alarmVM.alarmTime.period == Period.AM) "AM" else "PM",
                onValueChanged = {
                    try {
                        alarmVM.onAlarmEvent(AlarmUIEvent.OnPeriodChange(it))
                    }
                    catch (e: Exception){
                        Log.d("AlarmLog", "${e.printStackTrace()}")
                    }
                },
                items = periodList,
                visibleItemsCount = 3,
                modifier = Modifier.weight(1f),
                textModifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 25.sp)
            )
        }
        Text(
            //text = "Alarm time: ${alarmHour}:${alarmMinute}:${alarmSec} ${alarmPeriod}", // + if (alarmVM.alarmPeriod == Period.AM) "AM" else "PM",
            text = "${alarmVM.alarmHour} : ${alarmVM.alarmMinute} " + if (alarmVM.alarmPeriod == Period.AM) "AM" else "PM",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    alarmVM.setContext(context)
                    alarmVM.onAlarmEvent(AlarmUIEvent.OnAlarmSchedule)
                },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50.dp))
                    .weight(2f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = orange,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Schedule alarm",
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(50.dp))
            // Pause/Resume button
            Button(
                onClick = {
                    alarmVM.onAlarmEvent(AlarmUIEvent.OnAlarmCancel)
                },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50.dp))
                    .weight(2f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = orange,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Cancel alarm",
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultAlarmPreview() {
    ClockAppTheme {
        AlarmScreen()
    }
}