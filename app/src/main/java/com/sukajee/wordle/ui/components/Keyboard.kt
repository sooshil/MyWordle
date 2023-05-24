package com.sukajee.wordle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sukajee.wordle.ui.screens.mainscreen.FIRST_ROW_CHARACTERS
import com.sukajee.wordle.ui.screens.mainscreen.SECOND_ROW_CHARACTERS
import com.sukajee.wordle.ui.screens.mainscreen.THIRD_ROW_CHARACTERS

@Composable
fun Keyboard(
    onKey: (Char) -> Unit,
    onBackSpace: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val firstRowKeyWidth by remember { mutableStateOf(this.maxWidth / 10 - 4.dp) }
        val secondRowKeyWidth by remember { mutableStateOf(this.maxWidth / 9 - 4.dp) }
        val thirdRowKeyWidth by remember { mutableStateOf(this.maxWidth / 8 - 6.dp) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FIRST_ROW_CHARACTERS.forEach {
                    Box(
                        modifier = Modifier
                            .size(width = firstRowKeyWidth, height = firstRowKeyWidth * 1.25f)
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(4.dp),
                                color = Color.DarkGray,
                            )
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.DarkGray)
                            .clickable { onKey(it) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SECOND_ROW_CHARACTERS.forEach {
                    Box(
                        modifier = Modifier
                            .size(width = secondRowKeyWidth, height = firstRowKeyWidth * 1.25f)
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(4.dp),
                                color = Color.DarkGray,
                            )
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.DarkGray)
                            .clickable { onKey(it) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                THIRD_ROW_CHARACTERS.forEach { char ->
                    Box(
                        modifier = Modifier
                            .size(
                                width = if (char
                                        .isBackSpace()
                                        .not()
                                )
                                    thirdRowKeyWidth
                                else thirdRowKeyWidth * 1.25f,
                                height = firstRowKeyWidth * 1.25f
                            )
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(4.dp),
                                color = Color.DarkGray,
                            )
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.DarkGray)
                            .clickable {
                                if (char
                                        .isBackSpace()
                                        .not()
                                )
                                    onKey(char)
                                else onBackSpace()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

private fun Char.isBackSpace() = this == '⌫'
