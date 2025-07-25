package com.albrecht3.jetpack_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.albrecht3.jetpack_calculator.buttonclass.CalculatorButton
import com.albrecht3.jetpack_calculator.buttonclass.CalculatorButtonType
import com.albrecht3.jetpack_calculator.ui.theme.Cyan
import com.albrecht3.jetpack_calculator.ui.theme.Jetpack_CalculatorTheme
import com.albrecht3.jetpack_calculator.ui.theme.Red


class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

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
                    val (uiText, setUiText) = remember {
                        mutableStateOf("0")
                    }
                    LaunchedEffect(uiText) {
                        if (uiText.startsWith("0") && uiText != "0") {
                            setUiText(uiText.substring(1))
                        }
                    }
                    val (input, setInput) = remember {
                        mutableStateOf<String?>(null)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column {
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = "Result here",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.onTertiary
                            )
                            Spacer(modifier = Modifier.height(32.dp))

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
                                    Keyboard(button = it, onClick = {
                                        when (it.type) {
                                            CalculatorButtonType.Normal -> {
                                                runCatching {
                                                    setUiText(uiText.toInt().toString() + it.text)
                                                }.onFailure { throwable -> setUiText(uiText + it.text) }
                                                setInput((input ?: "") + it.text)
                                                if (viewModel.action.value.isNotEmpty()) {
                                                    if (viewModel.secondNumber.value == null) {
                                                        viewModel.setSecondNumber(it.text!!.toDouble())
                                                    } else {
                                                        if (viewModel.secondNumber.value.toString().split(".")[1]=="0"){
                                                            viewModel.setSecondNumber((viewModel.secondNumber.value.toString().split(".").first() + it.text!!).toDouble())
                                                        }else{
                                                            viewModel.setSecondNumber((viewModel.secondNumber.value.toString() + it.text!!).toDouble())
                                                        }
                                                    }
                                                }
                                            }

                                            CalculatorButtonType.Action -> {
                                                if (it.text == "=") {
                                                    val result = viewModel.getResult()
                                                    setUiText(result.toString())
                                                    setInput(null)
                                                    viewModel.resetAll()
                                                } else {
                                                    runCatching {
                                                        setUiText(
                                                            uiText.toInt().toString() + it.text
                                                        )
                                                    }.onFailure { throwable -> setUiText(uiText + it.text) }
                                                    if (input != null) {
                                                        if (viewModel.firstNumber.value == null) {
                                                            viewModel.setFirstNumber(input.toDouble())
                                                        } else {
                                                            viewModel.setSecondNumber(input.toDouble())
                                                        }
                                                        viewModel.setAction(it.text!!)
                                                    }
                                                }
                                            }

                                            CalculatorButtonType.Reset -> {
                                                setUiText("")
                                                setInput(null)
                                                viewModel.resetAll()
                                            }
                                        }
                                    })
                                }
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

