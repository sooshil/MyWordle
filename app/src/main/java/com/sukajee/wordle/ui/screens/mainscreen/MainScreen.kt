package com.sukajee.wordle.ui.screens.mainscreen

import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sukajee.wordle.R
import com.sukajee.wordle.navigation.Screen
import com.sukajee.wordle.ui.components.AnimatedText
import com.sukajee.wordle.ui.components.CustomDialog
import com.sukajee.wordle.ui.components.Keyboard
import com.sukajee.wordle.ui.components.MenuIcon
import com.sukajee.wordle.ui.components.MenuIconNames
import com.sukajee.wordle.ui.components.TopBar
import com.sukajee.wordle.ui.previews.FontScalePreviews
import com.sukajee.wordle.ui.previews.OrientationPreviews
import com.sukajee.wordle.util.ButtonType
import com.sukajee.wordle.util.DialogType
import com.sukajee.wordle.util.ErrorType
import com.sukajee.wordle.util.Strings
import com.sukajee.wordle.util.WordleEvent
import com.sukajee.wordle.util.getBorderColor
import com.sukajee.wordle.util.getCellColor
import com.sukajee.wordle.util.padWithZeros
import com.sukajee.wordle.R.string as strings

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val state by viewModel.gameState.collectAsState()
    val keyState by viewModel.keyState.collectAsState()
    val currentWordleEntry by viewModel.currentWordleEntry.collectAsState()

    StateLessMainScreen(
        currentWord = currentWordleEntry.word,
        state = state,
        onEvent = { viewModel.onEvent(it) },
        onMenuClick = {
            when (it.iconName) {
                MenuIconNames.HELP -> navController.navigate(Screen.HelpScreen.route)
                MenuIconNames.STATISTICS -> navController.navigate(Screen.StatsScreen.route)
                else -> Log.e("MainScreen", "The menu item type ${it.iconName} is not handled.")
            }
        },
        keyState = keyState
    )
}

@Composable
fun StateLessMainScreen(
    currentWord: String,
    state: GameUiState,
    keyState: KeyState,
    onEvent: (WordleEvent) -> Unit,
    modifier: Modifier = Modifier,
    onMenuClick: (MenuIcon) -> Unit
) {
    val configuration = LocalConfiguration.current
    val portrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = "WORD NO - ${state.currentWordNumber.toString().padWithZeros()}",
                screenName = Screen.HomeScreen,
                onNavigationIconClick = {},
                onMenuIconClick = onMenuClick
            )
        }
    ) { padding ->
        if (portrait) {
            Spacer(modifier = Modifier.height(32.dp))

            state.error?.let {
                when (it) {
                    is ErrorType.WordNotFound -> CustomDialog(
                        animatingIconPath = R.raw.warning,
                        title = stringResource(id = R.string.word_not_found_title),
                        message = stringResource(
                            id = R.string.word_not_found_message,
                            it.enteredWord.trim()
                        ),
                        positiveButtonText = stringResource(id = R.string.ok),
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

                    is ErrorType.WordLengthNotValid -> CustomDialog(
                        animatingIconPath = R.raw.warning,
                        title = stringResource(id = R.string.incorrect_word_length),
                        message = pluralStringResource(
                            id = R.plurals.incorrect_word_length_message,
                            count = it.enteredWord.trim().length,
                            it.enteredWord.trim(),
                            it.enteredWord.trim().length
                        ),
                        positiveButtonText = stringResource(id = R.string.ok),
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
                        animatingIconPath = if (won) R.raw.checkmark else R.raw.crossmark,
                        title = stringResource(id = if (won) Strings.getPositiveStrings() else Strings.getNegativeStrings()),
                        message = if (won) stringResource(id = Strings.wordFoundMessage()) else stringResource(
                            id = Strings.wordNotFoundMessage(), currentWord
                        ),
                        positiveButtonText = stringResource(id = R.string.ok),
                        onPositiveButtonClick = {
                            onEvent(
                                WordleEvent.OnDialogButtonClick(
                                    buttonType = ButtonType.POSITIVE,
                                    dialogType = DialogType.GAME_OVER_DIALOG,
                                    hasWon = won
                                )
                            )
                        },
                        negativeButtonText = stringResource(id = R.string.dismiss),
                        onNegativeButtonClick = {
                            onEvent(
                                WordleEvent.OnDialogButtonClick(
                                    buttonType = ButtonType.NEGATIVE,
                                    dialogType = DialogType.GAME_OVER_DIALOG,
                                    hasWon = won
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
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = CenterHorizontally
    ) {
        val context = LocalContext.current
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        Column {
            Spacer(modifier = Modifier.height(32.dp))
            repeat(6) { eachRow ->
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    repeat(5) { eachColumn ->
                        Spacer(modifier = Modifier.width(5.dp))
                        val cell = state.grid[eachRow][eachColumn]
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(getCellColor(cell = cell))
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(5.dp),
                                    color = getBorderColor(cell = cell)
                                ),
                            contentAlignment = Center
                        ) {
                            AnimatedText(
                                text = (state.grid[eachRow][eachColumn].char).toString(),
                                cell = cell
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
            onKey = {
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f)
                onEvent(WordleEvent.OnKey(it))
            },
            onBackSpace = {
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE, 1.0f)
                onEvent(WordleEvent.OnBackSpace)
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
//            Button(
//                onClick = { onEvent(WordleEvent.OnHint) }
//            ) {
//                Text(text = stringResource(id = strings.hint))
//            }
            Button(
                onClick = {
                    onEvent(
                        state.showStartNewGameButton?.let {
                            WordleEvent.OnStartNewWordButtonClick(it)
                        } ?: WordleEvent.OnSubmit
                    )
                }
            ) {
                Text(
                    text = stringResource(
                        id = state.showStartNewGameButton?.let {
                            strings.new_game
                        } ?: strings.check
                    ),
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
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
        keyState = KeyState(),
        onMenuClick = {}
    )
}
