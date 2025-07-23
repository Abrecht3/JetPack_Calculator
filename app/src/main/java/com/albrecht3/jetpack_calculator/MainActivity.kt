package com.albrecht3.jetpack_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.albrecht3.jetpack_calculator.buttonclass.CalculatorButton
import com.albrecht3.jetpack_calculator.buttonclass.CalculatorButtonType
import com.albrecht3.jetpack_calculator.ui.theme.Cyan
import com.albrecht3.jetpack_calculator.ui.theme.DarkWhite
import com.albrecht3.jetpack_calculator.ui.theme.Jetpack_CalculatorTheme
import com.albrecht3.jetpack_calculator.ui.theme.Red

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jetpack_CalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.background) {
                    val calculatorButtons = remember {
                        mutableListOf(
                            CalculatorButton("AC", CalculatorButtonType.Reset),
                            CalculatorButton("AC", CalculatorButtonType.Reset),
                            CalculatorButton("AC", CalculatorButtonType.Reset),
                            CalculatorButton("/", CalculatorButtonType.Action),
                            CalculatorButton("7", CalculatorButtonType.Normal),
                            CalculatorButton("8", CalculatorButtonType.Normal),
                            CalculatorButton("9", CalculatorButtonType.Normal),
                            CalculatorButton("x", CalculatorButtonType.Action),
                            CalculatorButton("4", CalculatorButtonType.Normal),
                            CalculatorButton("5", CalculatorButtonType.Normal),
                            CalculatorButton("6", CalculatorButtonType.Normal),
                            CalculatorButton("-", CalculatorButtonType.Action),
                            CalculatorButton("1", CalculatorButtonType.Normal),
                            CalculatorButton("2", CalculatorButtonType.Normal),
                            CalculatorButton("3", CalculatorButtonType.Normal),
                            CalculatorButton("+", CalculatorButtonType.Action),
                            CalculatorButton(
                                icon = Icons.Outlined.Refresh,
                                type = CalculatorButtonType.Normal
                            ),
                            CalculatorButton("0", CalculatorButtonType.Normal),
                            CalculatorButton(".", CalculatorButtonType.Normal),
                            CalculatorButton("=", CalculatorButtonType.Action),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                                .background(colorScheme.primary)
                                .padding(8.dp),
                            columns = GridCells.Fixed(4),
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            items(calculatorButtons) {
                                Keyboard(button = it, onClick = {})
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Keyboard(button: CalculatorButton, onClick: () -> Unit) {
    val btnBackground =
        if (button.type == CalculatorButtonType.Normal) colorScheme.secondary else if (button.type == CalculatorButtonType.Action) Red else Cyan
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(64.dp))
            .background(btnBackground)
            .fillMaxHeight()
            .aspectRatio(1f)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        if (button.text != null) {
            Text(
                button.text,
                color = colorScheme.onTertiary,
                fontWeight = FontWeight.Bold,
                fontSize = if (button.type == CalculatorButtonType.Action) 25.sp else 20.sp
            )
        } else {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = button.icon!!,
                contentDescription = null,
                tint = colorScheme.onTertiary
            )
        }
    }
}

