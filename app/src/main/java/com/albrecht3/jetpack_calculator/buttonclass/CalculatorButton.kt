package com.albrecht3.jetpack_calculator.buttonclass

import androidx.compose.ui.graphics.vector.ImageVector

data class CalculatorButton(
    val text: String? = null,
    val type: CalculatorButtonType,
    val icon: ImageVector? = null
)
