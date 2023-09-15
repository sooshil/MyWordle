package com.sukajee.wordle.ui.screens.helpscreen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sukajee.wordle.R
import com.sukajee.wordle.navigation.Screen
import com.sukajee.wordle.ui.components.HelpScreenScreenShot
import com.sukajee.wordle.ui.components.NumberedParagraph
import com.sukajee.wordle.ui.components.TopBar
import com.sukajee.wordle.ui.theme.VazirmatnFontFamily

@Composable
fun HelpScreen(navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.help).uppercase(),
                screenName = Screen.HelpScreen,
                onNavigationIconClick = { navController.popBackStack() },
                onMenuIconClick = { navController.popBackStack() }
            )
        }
    ) { padding ->
        var helps by remember {
            mutableStateOf<List<HelpEntry>>(emptyList())
        }

        LaunchedEffect(key1 = true) {
            helps = getHelps(context)
        }

        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(items = helps) { index, entry ->
                    Spacer(modifier = Modifier.height(8.dp))
                    entry.introText?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = VazirmatnFontFamily
                            ),
                            textAlign = TextAlign.Justify,
                            text = it
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    entry.helpText?.let { text ->
                        NumberedParagraph(
                            number = index,
                            text = text
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    entry.imageId?.let { imageId ->
                        HelpScreenScreenShot(
                            imageId = imageId,
                            contentDesc = entry.imageContentDescription ?: ""
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }
}

fun getHelps(context: Context): List<HelpEntry> {
    return listOf(
        HelpEntry(
            introText = context.getString(R.string.help_screen_intro_text),
        ),
        HelpEntry(
            helpText = context.getString(R.string.help_screen_help_one_text),
            imageId = R.drawable.screenshot_one,
            imageContentDescription = context.getString(R.string.help_screen_images_content_desc)
        ),
        HelpEntry(
            helpText = context.getString(R.string.help_screen_help_two_text),
            imageId = R.drawable.screenshot_two,
            imageContentDescription = context.getString(R.string.help_screen_images_content_desc)
        ),
        HelpEntry(
            helpText = context.getString(R.string.help_screen_help_three_text),
            imageId = R.drawable.screenshot_two,
            imageContentDescription = context.getString(R.string.help_screen_images_content_desc)
        ),
        HelpEntry(
            helpText = context.getString(R.string.help_screen_help_four_text),
            imageId = R.drawable.screenshot_three,
            imageContentDescription = context.getString(R.string.help_screen_images_content_desc)
        ),
        HelpEntry(
            helpText = context.getString(R.string.help_screen_help_five_text),
            imageId = R.drawable.screenshot_four,
            imageContentDescription = context.getString(R.string.help_screen_images_content_desc)
        ),
        HelpEntry(
            introText = context.getString(R.string.help_screen_bottom_text)
        )
    )
}

data class HelpEntry(
    val introText: String? = null,
    val helpText: String? = null,
    val imageId: Int? = null,
    val imageContentDescription: String? = null
)
