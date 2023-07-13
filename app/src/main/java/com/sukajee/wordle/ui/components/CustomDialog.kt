package com.sukajee.wordle.ui.components

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sukajee.wordle.R

@Composable
fun CustomDialog(
    @RawRes animatingIconPath: Int? = R.raw.checkmark,
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
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        animatingIconPath?.let {
                            val composition by rememberLottieComposition(
                                spec = LottieCompositionSpec.RawRes(it)
                            )
                            val progress by animateLottieCompositionAsState(
                                composition = composition,
                                iterations = LottieConstants.IterateForever
                            )
                            LottieAnimation(composition = composition, progress = { progress })
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                    title?.let {
                        Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = message,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        negativeButtonText?.let {
                            Button(
                                onClick = onNegativeButtonClick,
                                shape = RoundedCornerShape(30.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentHeight()
                            ) {
                                Text(
                                    text = negativeButtonText,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                        }

                        Button(
                            onClick = onPositiveButtonClick,
                            shape = RoundedCornerShape(30.dp),
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight()
                        ) {
                            Text(
                                text = positiveButtonText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
