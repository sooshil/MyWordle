package com.sukajee.wordle.ui.screens.mainscreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sukajee.wordle.R
import com.sukajee.wordle.ui.Cell
import com.sukajee.wordle.ui.KeyState
import com.sukajee.wordle.ui.GameUiState
import com.sukajee.wordle.ui.components.CustomDialog
import com.sukajee.wordle.ui.components.Keyboard
import com.sukajee.wordle.ui.components.TopBar
import com.sukajee.wordle.ui.previews.FontScalePreviews
import com.sukajee.wordle.ui.previews.OrientationPreviews
import com.sukajee.wordle.util.ButtonType
import com.sukajee.wordle.util.DialogType
import com.sukajee.wordle.util.ErrorType
import com.sukajee.wordle.util.WordleEvent
import com.sukajee.wordle.util.getBorderColor
import com.sukajee.wordle.util.getCellColor
import com.sukajee.wordle.util.getCharColor
import com.sukajee.wordle.R.string as strings

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val state by viewModel.gameState.collectAsState()
    val keyState by viewModel.keyState.collectAsState()
    val currentWord by viewModel.currentWord.collectAsState()
    rememberCoroutineScope()

    StateLessMainScreen(
        currentWord = currentWord,
        state = state,
        onEvent = { viewModel.onEvent(it) },
        keyState = keyState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessMainScreen(
    currentWord: String,
    state: GameUiState,
    keyState: KeyState,
    onEvent: (WordleEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val portrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(title = currentWord) //stringResource(id = strings.app_name))
        }
    ) { padding ->
        if (portrait) {
            Spacer(modifier = Modifier.height(32.dp))

            state.error?.let {
                when (it) {
                    is ErrorType.WordNotFound -> CustomDialog(
                        title = stringResource(id = R.string.word_not_found_title),
                        message = stringResource(
                            id = R.string.word_not_found_message,
                            it.enteredWord
                        ),
                        positiveButtonText = stringResource(id = R.string.yes),
                        onPositiveButtonClick = {
                            onEvent(
                                WordleEvent.OnDialogButtonClick(
                                    dialogType = DialogType.ERROR_DIALOG,
                                    buttonType = ButtonType.POSITIVE
                                )
                            )
                        },
                        onDismiss = { }
                    )
                }
            }

            state.isGameOver?.let {
                if (it) {
                    val won = state.hasWon ?: false
                    CustomDialog(
                        title = stringResource(id = if (won) R.string.you_won else R.string.you_lost),
                        message = stringResource(id = R.string.start_new_game),
                        positiveButtonText = stringResource(id = R.string.yes),
                        onPositiveButtonClick = {
                            onEvent(
                                WordleEvent.OnDialogButtonClick(
                                    buttonType = ButtonType.POSITIVE,
                                    dialogType = DialogType.GAME_OVER_DIALOG
                                )
                            )
                        },
                        onDismiss = { }
                    )
                }
            }

            PortraitMainScreen(
                padding = padding,
                state = state,
                keyState = keyState,
                onEvent = onEvent
            )
        } else {
            PortraitMainScreen(
                padding = padding,
                state = state,
                keyState = keyState,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun PortraitMainScreen(
    padding: PaddingValues,
    state: GameUiState,
    keyState: KeyState,
    onEvent: (WordleEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            ),
        horizontalAlignment = CenterHorizontally
    ) {
        Column {
            Spacer(modifier = Modifier.height(56.dp))
            repeat(6) { eachRow ->
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    repeat(5) { eachColumn ->
                        Spacer(modifier = Modifier.width(5.dp))
                        val cell = state.grid[eachRow][eachColumn]
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(getCellColor(cell = cell))
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(5.dp),
                                    color = getBorderColor(cell = cell)
                                ),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = (state.grid[eachRow][eachColumn].char).toString(),
                                style = MaterialTheme.typography.headlineMedium,
                                color = getCharColor(cell = cell)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Keyboard(
            keyState = keyState,
            onKey = { onEvent(WordleEvent.OnKey(it)) },
            onBackSpace = { onEvent(WordleEvent.OnBackSpace) }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onEvent(WordleEvent.OnHint) }
            ) {
                Text(text = stringResource(id = strings.hint))
            }
            Button(
                onClick = { onEvent(WordleEvent.OnSubmit) }
            ) {
                Text(text = stringResource(id = strings.check))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun LandscapeMainScreen(padding: PaddingValues) {

}

@FontScalePreviews
@OrientationPreviews
@Composable
fun MainScreenPreview() {
    StateLessMainScreen(
        currentWord = "OTHER",
        state = GameUiState(
            grid = mutableListOf(
                mutableListOf(Cell('A'), Cell('B'), Cell('C'), Cell('D'), Cell('E')),
                mutableListOf(Cell('B'), Cell('G'), Cell('H'), Cell('M'), Cell('N')),
                mutableListOf(Cell('C'), Cell('F'), Cell('I'), Cell('L'), Cell('O')),
                mutableListOf(Cell('D'), Cell('E'), Cell('J'), Cell('K'), Cell('P')),
            ),
            currentGridIndex = Pair(0, 0)
        ),
        onEvent = {},
        keyState = KeyState()
    )
}
