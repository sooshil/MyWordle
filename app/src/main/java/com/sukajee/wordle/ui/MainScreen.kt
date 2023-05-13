package com.sukajee.wordle.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sukajee.wordle.R.string as strings

const val FIRST_ROW_CHARACTERS = "QWERTYUIOP"
const val SECOND_ROW_CHARACTERS = "ASDFGHJKL"
const val THIRD_ROW_CHARACTERS = "ZXCVBNM⌫"

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val state by viewModel.gameState.collectAsState()
    StateLessMainScreen(
        state = state,
        onKey = { viewModel.onKey(it) },
        onBackSpace = { viewModel.onBackSpace() },
        onSubmitClick = { viewModel.onSubmit() },
        onHintClick = { viewModel.onHint() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessMainScreen(
    state: GameUiState,
    onKey: (Char) -> Unit,
    onBackSpace: () -> Unit,
    onSubmitClick: () -> Unit,
    onHintClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val portrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(id = strings.app_name))
        }
    ) { padding ->
        if (portrait) {
            Spacer(modifier = Modifier.height(32.dp))
            PortraitMainScreen(
                padding = padding,
                state = state,
                onKey = onKey,
                onBackSpace = onBackSpace,
                onSubmitClick = onSubmitClick,
                onHintClick = onHintClick
            )
        } else {
            LandscapeMainScreen(padding)
        }
    }
}

@Composable
fun PortraitMainScreen(
    padding: PaddingValues,
    state: GameUiState,
    onKey: (Char) -> Unit,
    onBackSpace: () -> Unit,
    onHintClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(56.dp))
            repeat(6) { eachRow ->
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    repeat(5) { eachColumn ->
                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(5.dp),
                                    color = Color.DarkGray
                                ),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = (state.grid[eachRow][eachColumn]).toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        Column {
            Keyboard(
                onKey = onKey,
                onBackSpace = onBackSpace
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onHintClick
                ) {
                    Text(text = stringResource(id = strings.hint))
                }
                Button(
                    onClick = onSubmitClick
                ) {
                    Text(text = stringResource(id = strings.check))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun LandscapeMainScreen(padding: PaddingValues) {

}

@Composable
fun Keyboard(
    onKey: (Char) -> Unit,
    onBackSpace: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Center
    ) {
        val firstRowKeyWidth by remember { mutableStateOf(this.maxWidth / 10 - 4.dp) }
        val secondRowKeyWidth by remember { mutableStateOf(this.maxWidth / 9 - 4.dp) }
        val thirdRowKeyWidth by remember { mutableStateOf(this.maxWidth / 8 - 6.dp) }
        Column(
            horizontalAlignment = CenterHorizontally
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
                        contentAlignment = Center
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
                        contentAlignment = Center
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
                        contentAlignment = Center
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

@FontScalePreviews
@OrientationPreviews
@Composable
fun MainScreenPreview() {
    StateLessMainScreen(
        state = GameUiState(
            grid = mutableListOf(
                mutableListOf('A', 'B', 'C', 'D', 'E'),
                mutableListOf('H', 'P', 'U', 'M', 'F'),
                mutableListOf('T', 'S', 'X', 'Z', 'W'),
                mutableListOf('U', 'R', 'M', 'L', 'G'),
                mutableListOf('V', 'Q', 'N', 'K', 'H'),
                mutableListOf('W', 'P', 'O', 'Z', 'I')
            ),
            currentGridIndex = Pair(0, 0)
        ),
        onBackSpace = {},
        onKey = {},
        onSubmitClick = {},
        onHintClick = {}
    )
}
