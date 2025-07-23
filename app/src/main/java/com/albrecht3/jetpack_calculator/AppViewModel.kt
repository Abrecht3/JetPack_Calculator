package com.albrecht3.jetpack_calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class AppViewModel: ViewModel() {
    private val firstNumber: MutableStateFlow<Double> = MutableStateFlow(0.0)
    private val secondNumber: MutableStateFlow<Double> = MutableStateFlow(0.0)

}