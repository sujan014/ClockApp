package com.example.clockapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clockapp.domain.util.Screens
import com.example.clockapp.ui.theme.ClockAppTheme
import com.example.clockapp.ui.theme.purple

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    AppScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppScreen() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar(navController)
        }
    ) {
        MainGraph(navController)
    }
}

/*
@Composable
fun TopBar(
    navController: NavController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.DarkGray)
                .clickable {
                    navController.navigate(Screens.Clock.route){
                        popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                        launchSingleTop = true      // avoid multiple copies of same destination when reselecting same item
                    }
                }
        ) {
            Text(
                text = "Clock",
                style = MaterialTheme.typography.h6,
                color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.DarkGray)
                .clickable {
                    navController.navigate(Screens.Timer.route){
                        popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                        launchSingleTop = true      // avoid multiple copies of same destination when reselecting same item
                    }
                }
        ) {
            Text(
                text = "Timer", style = MaterialTheme.typography.h6, color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.DarkGray)
                .clickable {
                    navController.navigate(Screens.Alarm.route){
                        popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                        launchSingleTop = true      // avoid multiple copies of same destination when reselecting same item
                    }
                }
        ) {
            Text(
                text = "Alarm", style = MaterialTheme.typography.h6, color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.DarkGray)
                .clickable {
                    navController.navigate(Screens.Stopwatch.route){
                    popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                    launchSingleTop = true      // avoid multiple copies of same destination when reselecting same item
                } }
        ) {
            Text(
                text = "Stopwatch", style = MaterialTheme.typography.h6, color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}*/

@Composable
fun TopBar(
    navController: NavController,
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        topBarButton(
            text = "Clock",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp)),
            index = 0,
            selectedIndex = selectedIndex,
            onClick = { number->
                selectedIndex = number
                navController.navigate(Screens.Clock.route) {
                    popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                    launchSingleTop =
                        true      // avoid multiple copies of same destination when reselecting same item
                }
            }
        )
        topBarButton(
            text = "Timer",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp)),
            index = 1,
            selectedIndex = selectedIndex,
            onClick = { number->
                selectedIndex = number
                navController.navigate(Screens.Timer.route) {
                    popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                    launchSingleTop =
                        true      // avoid multiple copies of same destination when reselecting same item
                }
            }
        )
        topBarButton(
            text = "Alarm",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp)),
            index = 2,
            selectedIndex = selectedIndex,
            onClick = { number->
                selectedIndex = number
                navController.navigate(Screens.Alarm.route) {
                    popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                    launchSingleTop =
                        true      // avoid multiple copies of same destination when reselecting same item
                }
            }
        )
        topBarButton(
            text = "Stopwatch",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp)),
            index = 3,
            selectedIndex = selectedIndex,
            onClick = { number->
                selectedIndex = number
                navController.navigate(Screens.Stopwatch.route) {
                    popUpTo(navController.graph.findStartDestination().id)  // go to first screen
                    launchSingleTop =
                        true      // avoid multiple copies of same destination when reselecting same item
                }
            }
        )
    }
}

@Composable
fun topBarButton(
    text: String = "",
    modifier: Modifier = Modifier,
    index: Int,
    selectedIndex: Int,
    onClick: (Int) -> Unit
){
    val backgroundColor = if (index == selectedIndex) purple
                            else Color.DarkGray

    Box(
        modifier = modifier
            .background(color = backgroundColor)
            .clickable {
                onClick(index)
            }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6,
            color = Color.White,
            modifier = Modifier.padding(5.dp)
        )
    }
}
@Composable
fun MainGraph(navController: NavHostController){
    NavHost(navController = navController,
        startDestination = Screens.Clock.route
    )
    {
        composable(route = Screens.Clock.route){
            ClockScreen()
        }
        composable(route = Screens.Timer.route){
            TimerScreen()
        }
        composable(route = Screens.Stopwatch.route){
            StopwatchScreen()
        }
        composable(route = Screens.Alarm.route){
            AlarmScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ClockAppTheme {
        AppScreen()
    }
}