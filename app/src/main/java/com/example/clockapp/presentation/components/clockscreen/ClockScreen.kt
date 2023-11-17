package com.example.clockapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clockapp.presentation.components.clockscreen.LiveClock
import com.example.clockapp.ui.theme.ClockAppTheme
import com.example.clockapp.ui.theme.shinyBlue
import kotlinx.coroutines.delay
import java.util.*

object GetMonth{
    operator fun invoke(month: Int): String{
        return when(month){
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4->"April"
            5->"May"
            6->"June"
            7->"July"
            8->"August"
            9->"September"
            10->"October"
            11->"November"
            12->"December"
            else -> "invalid month"
        }
    }
}

object GetWeekInWords{
    operator fun invoke(week: Int): String{
        return when(week){
            1->"Sunday"
            2->"Monday"
            3->"Tuesday"
            4->"Wednesday"
            5->"Thursday"
            6->"Friday"
            7->"Saturday"
            else->"Invalid data"
        }
    }
}
@Composable
fun ClockScreen(){
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    Spacer(modifier = Modifier.height(30.dp))
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LaunchedEffect(key1 = currentTime){
            while(true){
                delay(1000L)
                currentTime = System.currentTimeMillis()
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(color = shinyBlue)
                .fillMaxWidth()
        ){
            val cal = Calendar.getInstance()
            val hours = cal.get(Calendar.HOUR_OF_DAY)
            val minutes = cal.get(Calendar.MINUTE)
            val seconds = cal.get(Calendar.SECOND)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Row(
                  //  horizontalArrangement = Arrangement.Center
                //) {
                    Text(
                        text = "${hours.toString().padStart(2, '0')}:${
                            minutes.toString().padStart(2, '0')
                        }:${seconds.toString().padStart(2, '0')}",
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontSize = 40.sp
                    )
                //}
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val cal = Calendar.getInstance()
                    val year = cal.get(Calendar.YEAR)
                    val month = cal.get(Calendar.MONTH)
                    val day = cal.get(Calendar.DAY_OF_MONTH)
                    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
                    val monthInWords = GetMonth(month)
                    val dayOfWeekInWords = GetWeekInWords(dayOfWeek)
                    Text(
                        text = "$monthInWords $day, $year",
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    )
                    Text(
                        text = "$dayOfWeekInWords",
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    )
                }
            }
        }

        /*Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = shinyBlue),
            ) {

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = shinyBlue),
                contentAlignment = Alignment.TopEnd
            ){

            }
        }*/
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            LiveClock(
                modifier = Modifier
                    .size(500.dp),
                time = {
                    currentTime
                },
                circleRadius = 400f,
                outerCircleThickness = 50f)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClockScreenPreview() {
    ClockAppTheme {
        ClockScreen()
    }
}