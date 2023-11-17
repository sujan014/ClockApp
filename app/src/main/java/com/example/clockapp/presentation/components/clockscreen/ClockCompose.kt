package com.example.clockapp.presentation.components.clockscreen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.clockapp.ui.theme.darkGray
import com.example.clockapp.ui.theme.gray
import com.example.clockapp.ui.theme.redOrange
import com.example.clockapp.ui.theme.white
import com.example.clockapp.ui.theme.*
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LiveClock(
    modifier: Modifier = Modifier,
    time:()-> Long,  // provide current time state
    circleRadius: Float,
    outerCircleThickness: Float
){

    var circleCenter by remember{ mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
    ){
        Canvas(
            modifier = Modifier.fillMaxSize()
        ){
            val width = size.width
            val height = size.height
            circleCenter = Offset(x=width/2f, y = height/2f)
            val date = Date(time())
            val cal = Calendar.getInstance()
            val hours = cal.get(Calendar.HOUR_OF_DAY)
            val minutes = cal.get(Calendar.MINUTE)
            val seconds = cal.get(Calendar.SECOND)

            Log.d("SYS_DateTime", "$hours:$minutes:$seconds")
            // outer circle
            drawCircle(
                style = Stroke(
                    width = outerCircleThickness
                ),
                brush = Brush.linearGradient(
                    listOf(
                        white.copy(0.45f),
                        darkGray.copy(0.35f)
                    )
                ),
                radius = circleRadius + outerCircleThickness/2f,
                center = circleCenter
            )
            // Main circle
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        white.copy(0.45f),
                        darkGray.copy(0.25f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )
            // middle dot
            drawCircle(
                color = gray,
                radius = 15f,
                center = circleCenter
            )

            val littleLineLength = circleRadius * 0.1f
            val largeLineLength = circleRadius * 0.2f

            for(i in 0 until 60){
                val angleInDegree = i * 360f/60f
                val angleInRad = angleInDegree * PI/180f + PI/2f    //(add 180 degree to map 0 degree from bottom to top)
                val lineLength = if (i%5 == 0) largeLineLength else littleLineLength
                val lineThickness = if (i%5 == 0) 5f else 2f

                // get the start (x,y) co-ordinate of each minutes.
                val start = Offset(
                    x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(angleInRad) + circleCenter.y).toFloat(),
                )
                // get the end (x,y) co-ordinate of each minutes.
                val end = Offset(
                    x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(angleInRad) + lineLength + circleCenter.y).toFloat(),
                )
                rotate(
                   angleInDegree + 180,
                   pivot = start
                ){
                // without rotation, the minutes marker will be vertical. so rotate the angle
                    drawLine(
                        color = gray,
                        start = start,
                        end = end,
                        strokeWidth = lineThickness.dp.toPx()
                    )
                }

                /*
                // another way to get end co-ordinates without rotation
                val end = Offset(
                    // get the end (x,y) co-ordinate of each minutes.
                    x = ((circleRadius - lineLength) * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = ((circleRadius - lineLength) * sin(angleInRad) + circleCenter.y).toFloat(),
                )
                // without rotation, the minutes marker will be vertical. so rotate the angle
                drawLine(
                    color = gray,
                    start = start,
                    end = end,
                    strokeWidth = lineThickness.dp.toPx()
                )*/
            }
            val clockHands = listOf(ClockHand.Seconds, ClockHand.Minutes, ClockHand.Hours)

            clockHands.forEach { clockHand ->
                val angleInDegrees = when(clockHand){
                    ClockHand.Seconds -> {
                        seconds * 360f/60f
                    }
                    ClockHand.Minutes -> {
                        (minutes + seconds/60f)*360f/60
                    }
                    ClockHand.Hours -> {
                        (((hours%12)/12f*60f)+minutes/12f) * 360f/60
                    }
                }
                val lineLength = when(clockHand){
                    ClockHand.Seconds -> {
                        circleRadius * 0.8f
                    }
                    ClockHand.Minutes -> {
                        circleRadius * 0.7f
                    }
                    ClockHand.Hours -> {
                        circleRadius * 0.5f
                    }
                }
                val lineThickness = when(clockHand){
                    ClockHand.Seconds -> {
                        3f
                    }
                    ClockHand.Minutes -> {
                        7f
                    }
                    ClockHand.Hours -> {
                        9f
                    }
                }
                val start = Offset(
                    x = circleCenter.x,
                    y = circleCenter.y
                )
                val end = Offset(
                    x = circleCenter.x,
                    y = circleCenter.y + lineLength
                )
                rotate(
                    angleInDegrees + 180, //(add 180 degree to map 0 degree from bottom to top)
                    start
                ){
                    drawLine(
                        color = if (clockHand == ClockHand.Seconds) redOrange else gray,
                        start = start,
                        end = end,
                        strokeWidth = lineThickness.dp.toPx()
                    )
                }
            }
        }
    }
}

enum class ClockHand{
    Seconds,
    Minutes,
    Hours
}