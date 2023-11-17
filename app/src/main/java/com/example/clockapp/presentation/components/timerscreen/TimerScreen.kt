package com.example.clockapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clockapp.presentation.components.CustomDialog
import com.example.clockapp.presentation.components.PickerCall
import com.example.clockapp.presentation.viewmodel.TimerVM
import com.example.clockapp.presentation.viewmodel.util.TimerUIEvent
import com.example.clockapp.ui.theme.*

// Need Internet Permission to run CountDownTimer
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun TimerScreen(
    timerVM: TimerVM = viewModel()
){
    var startState by remember { mutableStateOf(false) }

    val countHour = timerVM.timerTime.hour
    val countMinute = timerVM.timerTime.min
    val countSecond = timerVM.timerTime.sec
    val btnPauseResume = timerVM.btnPauseResume

    val context = LocalContext.current
    var alarmplay: MediaPlayer = MediaPlayer.create(context, R.raw.alarmfile)
    var alarmState by remember { mutableStateOf(false) }

    var scaffoldState = rememberScaffoldState()

    val hoursPickList = remember { (0..23).map { it.toString() } }
    val minutesPickList = remember { (0..59).map{ it.toString()} }
    val secondsPickList = remember { (0..59).map{ it.toString()} }

    LaunchedEffect(key1 = true){
        timerVM.timerEvent.collect { event ->
            when(event){
                is TimerUIEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is TimerUIEvent.TimerComplete -> {
                    alarmState = true
                    startState = false
                }
                is TimerUIEvent.MainTab -> {
                    startState = false
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Timer not set."
                    )
                }
                else -> Unit    // we don't do anything
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = abbey),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Time Picker
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .height(320.dp)
                    .background(color = Color.Black)
                    .fillMaxWidth(0.8f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                ) {

                    //Text("Hour", color = Color.White)
                    PickerCall(
                        //value = alarmVM.alarmTime.hour.toString(),//alarmHour.toString(),
                        value = timerVM.timerTime.hour.toString(),
                        onValueChanged = {hour ->
                            try {
                                //alarmHour = it.toInt()
                                val temp = hour.toInt()
                                timerVM.setHour(temp)
                            }
                            catch (e: Exception){
                                Log.d("AlarmLog", "timer hour ${e.printStackTrace()}")
                            }
                        },
                        items = hoursPickList,
                        visibleItemsCount = 5,
                        modifier = Modifier.weight(1f),
                        textModifier = Modifier.padding(8.dp),
                        textStyle = TextStyle(fontSize = 25.sp)
                    )
                    /*Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        modifier = Modifier.height(230.dp)
                    ) {
                        items((1..24).toList()) { hourPick ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color = orange)
                                    .padding(vertical = 10.dp, horizontal = 30.dp)
                                    .clickable {
                                        timerVM.setHour(hourPick)
                                    }
                            ) {
                                Text(
                                    text = "${hourPick.toString().padStart(2, '0')}",
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }*/

                    //Spacer(modifier = Modifier.width(10.dp))


                        //Text("Min", color = Color.White)
                        PickerCall(
                            //value = alarmVM.alarmTime.hour.toString(),//alarmHour.toString(),
                            value = timerVM.timerTime.min.toString(),
                            onValueChanged = {minute ->
                                try {
                                    //alarmHour = it.toInt()
                                    val temp = minute.toInt()
                                    timerVM.setMin(temp)
                                }
                                catch (e: Exception){
                                    Log.d("AlarmLog", "timer minute ${e.printStackTrace()}")
                                }
                            },
                            items = minutesPickList,
                            visibleItemsCount = 5,
                            modifier = Modifier.weight(1f),
                            textModifier = Modifier.padding(8.dp),
                            textStyle = TextStyle(fontSize = 25.sp)
                        )
                        /*Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(
                            modifier = Modifier.height(230.dp)
                        ) {
                            items((1..60).toList()) { minutePick ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(color = orange)
                                        .padding(vertical = 10.dp, horizontal = 30.dp)
                                        .clickable {
                                            timerVM.setMin(minutePick)
                                        }
                                ) {
                                    Text(
                                        text = "${minutePick.toString().padStart(2, '0')}",
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }*/

                    //Spacer(modifier = Modifier.width(10.dp))


                        //Text("Sec", color = Color.White)
                        PickerCall(
                            //value = alarmVM.alarmTime.hour.toString(),//alarmHour.toString(),
                            value = timerVM.timerTime.sec.toString(),
                            onValueChanged = {second ->
                                try {
                                    //alarmHour = it.toInt()
                                    val temp = second.toInt()
                                    timerVM.setSec(temp)
                                }
                                catch (e: Exception){
                                    Log.d("AlarmLog", "timer second ${e.printStackTrace()}")
                                }
                            },
                            items = secondsPickList,
                            visibleItemsCount = 5,
                            modifier = Modifier.weight(1f),
                            textModifier = Modifier.padding(8.dp),
                            textStyle = TextStyle(fontSize = 25.sp)
                        )
                        /*Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(
                            modifier = Modifier.height(230.dp)
                        ) {
                            items((1..60).toList()) { secondPick ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(color = orange)
                                        .padding(vertical = 10.dp, horizontal = 30.dp)
                                        .clickable {
                                            timerVM.setSec(secondPick)
                                        }
                                ) {
                                    Text(
                                        text = "${secondPick.toString().padStart(2, '0')}",
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }*/

                }
            }

            // Selected Time
            Row(
                modifier = Modifier
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Black)
                        .padding(vertical = 10.dp, horizontal = 30.dp)
                ) {
                    Text(
                        text = "${timerVM.rtcHour}",//"${timerVM.timerTime.hour}",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Black)
                        .padding(vertical = 10.dp, horizontal = 30.dp)
                ) {
                    Text(
                        text = "${timerVM.rtcMin}",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Black)
                        .padding(vertical = 10.dp, horizontal = 30.dp)
                ) {
                    Text(
                        text = "${timerVM.rtcSec}",
                        color = Color.White
                    )
                }
            }
            if (alarmState){
                alarmplay.start()
                CustomDialog(
                    icon = Icons.Filled.Notifications,
                    iconDescription = "Notification",
                    header = "Timer Notification",
                    headerColor = Purple500,
                    body = "Timer completed. Press confirm to exit this notification",
                    bodyColor = Purple500,
                    confirmButton = {
                        if (alarmplay.isPlaying) alarmplay.stop()
                        alarmState = false
                    }
                )

                /*AlertDialog(
                    onDismissRequest = {
                        if (alarmplay.isPlaying) alarmplay.stop()
                        alarmState = false
                    },
                    title = { Text(text = "Timer alert", color = Color.Black) },
                    text = {
                        Text(
                            text = "Timer complete. Press to mute",
                            color = Color.Black,
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (alarmplay.isPlaying) alarmplay.stop()
                                alarmState = false
                            },
                        ) {
                            Text(text = "Confirm", color = Color.White)
                        }
                    },
                    backgroundColor = Color.Green,
                    contentColor = Color.White,
                    properties = DialogProperties(dismissOnClickOutside = false),
                )*/
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (!startState) {
                // Start button
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            startState = true
                            timerVM.onStart()
                        },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50.dp))
                            .weight(2f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Start")
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Reset button
                    Button(
                        onClick = {
                            startState = false
                            timerVM.onDelete()
                        },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50.dp))
                            .weight(2f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = shinyBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Delete")
                    }
                    Spacer(modifier = Modifier.width(50.dp))
                    // Pause/Resume button
                    Button(
                        onClick = {
                            timerVM.onPauseResume()
                        },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50.dp))
                            .weight(2f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = btnPauseResume.color,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "${btnPauseResume.text}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    ClockAppTheme {
        TimerScreen()
    }
}