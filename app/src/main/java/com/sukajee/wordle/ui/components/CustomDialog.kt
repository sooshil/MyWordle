package com.sukajee.wordle.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    title: String? = null,
    message: String,
    positiveButtonText: String,
    onPositiveButtonClick: () -> Unit,
    negativeButtonText: String? = null,
    onNegativeButtonClick: () -> Unit = { },
    onDismiss: () -> Unit = { }
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    title?.let {
                        Text(
                            text = title,
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = message,
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily.Default
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        negativeButtonText?.let {
                            Button(
                                onClick = onNegativeButtonClick,
                                shape = RoundedCornerShape(30.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                            ) {
                                Text(
                                    text = negativeButtonText,
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily.Default
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                        }

                        Button(
                            onClick = onPositiveButtonClick,
                            shape = RoundedCornerShape(30.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                        ) {
                            Text(
                                text = positiveButtonText,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily.Default
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
