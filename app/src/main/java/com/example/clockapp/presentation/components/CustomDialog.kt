package com.example.clockapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.clockapp.ui.theme.Purple500
import com.example.clockapp.ui.theme.blueGray
import com.example.clockapp.ui.theme.lightPurple
import com.example.clockapp.ui.theme.shinyBlue

@Composable
fun CustomDialog(
    icon: ImageVector?,
    iconDescription: String,
    header: String,
    headerColor: Color,
    body: String,
    bodyColor: Color,
    confirmButton: () -> Unit,
) {
    //var displayState by remember { mutableStateOf(true) }
    //if (displayState) {
    Dialog(onDismissRequest = {
        confirmButton()
        //displayState = false
    }) {
        CustomDialogUI(
            icon = icon,
            iconDescription = iconDescription,
            header = header,
            headerColor = headerColor,
            body = body,
            bodyColor = bodyColor,
            ConfirmPress = {
                confirmButton()
                //displayState = false
            }
        )
    }
    //}
}

//Layout
@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    icon: ImageVector?,
    iconDescription: String?,
    header: String,
    headerColor: Color,
    body: String,
    bodyColor: Color,
    ConfirmPress: () -> Unit,
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier.background(Color.White)
        ) {
            //.......................................................................
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = iconDescription?:"",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                Text(
                    text = header,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h6,
                    color = headerColor
                )
                Text(
                    text = body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1,
                    color = bodyColor
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(lightPurple),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = blueGray,
                    ),
                    onClick = {
                        ConfirmPress()
                    }
                ) {
                    Text(
                        "Confirm",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}