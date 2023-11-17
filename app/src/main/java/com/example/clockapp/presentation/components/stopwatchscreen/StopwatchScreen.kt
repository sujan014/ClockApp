package com.example.clockapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clockapp.presentation.viewmodel.StopwatchVM
import com.example.clockapp.ui.theme.*

@Composable
fun StopwatchScreen(viewModel:StopwatchVM = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.8f)
                .background(color = orange),
            contentAlignment = Alignment.Center
        )
        {
            Row(
                modifier = Modifier
                    .padding(vertical = 30.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text("${viewModel.swTime.hour.toString().padStart(2,'0')}",fontSize = 50.sp, color = blue)
                Text(":",fontSize = 50.sp, color = blue)
                Text("${viewModel.swTime.min.toString().padStart(2,'0')}",fontSize = 50.sp, color = blue)
                Text(":",fontSize = 50.sp, color = blue)
                Text("${viewModel.swTime.sec.toString().padStart(2,'0')}",fontSize = 50.sp, color = blue)
                //Text(":",fontSize = 30.sp, color = blue)
                //Text("${viewModel.swTime.millis10.toString().padStart(2,'0')}",fontSize = 30.sp, color = purple)
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth(0.8f)
                .background(color = Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(color = viewModel.btnMain.color)
                        .clickable {  viewModel.onBtnMain() },
                    contentAlignment = Alignment.Center
                ){
                    Text("${viewModel.btnMain.text}", fontSize = 20.sp, color = white)
                }
                /*OutlinedButton(
                    onClick = { viewModel.onBtnMain() },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = viewModel.btnMain.color,
                        contentColor = Color.White
                    )
                ) {
                    Text("${viewModel.btnMain.text}", fontSize = 20.sp)
                }*/
                Spacer(modifier = Modifier.width(30.dp))
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {  viewModel.onBtnSide() }
                        .background(color = viewModel.btnSide.color),
                    contentAlignment = Alignment.Center
                ){
                    Text("${viewModel.btnSide.text}", fontSize = 20.sp, color = Color.Black)
                }
                /*OutlinedButton(
                    onClick = { viewModel.onBtnSide() },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = viewModel.btnSide.color,
                        contentColor = Color.White
                    )
                ) {
                    Text("${viewModel.btnSide.text}", fontSize = 20.sp)
                }*/
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StopwatchPreview() {
    ClockAppTheme {
        StopwatchScreen()
    }
}