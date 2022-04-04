package com.jetpack.wordeffectgraphics

import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.wordeffectgraphics.ui.theme.WordEffectGraphicsTheme
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordEffectGraphicsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Word Effect",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        WordEffectGraphics()
                    }
                }
            }
        }
    }
}

@Composable
fun WordEffectGraphics() {
    val text = "LEARN"
    val letterCount = text.count()
    val padding = 24.dp
    var enabled by remember { mutableStateOf(true) }
    val transition = updateTransition(targetState = enabled, label = "")

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(letterCount) { count ->
                val firstDelay = 250
                val firstDuration = 500
                val secondDelay = 100
                val secondDuration = 500
                val previousDelay = (letterCount - 1) * firstDelay + firstDuration
                val rotationX by transition.animateFloat(
                    transitionSpec = {
                        tween(
                            delayMillis = count * firstDelay,
                            durationMillis = firstDuration
                        )
                    },
                    label = ""
                ) {
                    if (it) 0f else 180f
                }

                val translationY by transition.animateFloat(
                    transitionSpec = {
                        tween(
                            delayMillis = previousDelay * count * secondDelay,
                            durationMillis = secondDuration,
                            easing = { BounceInterpolator().getInterpolation(it) }
                        )
                    },
                    label = ""
                ) {
                    if (it) 0f else 180f
                }

                DoubleSide(
                    flipType = FLIPTYPE.HORIZONTAL,
                    rotationX = rotationX,
                    translationY = -100 * sin(translationY * Math.PI.toFloat() / 180f),
                    cameraDistance = 100f,
                    front = {
                        LetterCard(
                            text = text[count].toString(),
                            color = Color.Black,
                            border = Color(58, 58, 60)
                        )
                    },
                    back = {
                        LetterCard(
                            text = text[count].toString(),
                            color = Color(95, 139, 85),
                            border = Color(95, 139, 85)
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                enabled = !enabled
            }
        ) {
            Text(text = "Click Me!")
        }
    }
}

@Composable
fun LetterCard(
    text: String,
    color: Color,
    border: Color
) {
    val roundedDegree = RoundedCornerShape(2.dp)
    Box(
        modifier = Modifier
            .clip(roundedDegree)
            .background(color)
            .size(70.dp)
            .border(2.dp, border, shape = roundedDegree),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color.White
        )
    }
}


















