package com.example.clockapp.domain.util

sealed class SwAction{
    object Start: SwAction()
    object Stop: SwAction()
    object Resume: SwAction()
    object Reset: SwAction()
}
