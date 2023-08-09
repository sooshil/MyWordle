package com.sukajee.wordle.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.wordle.R
import com.sukajee.wordle.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    screenName: Screen,
    titleColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = { }
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        title = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    fontSize = 18.sp
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = titleColor
            /**/
//            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
//            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        navigationIcon = {
            if (screenName != Screen.HomeScreen) {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            when (screenName) {
                Screen.HelpScreen -> {}
                Screen.HomeScreen -> {
                    TopBarIcons.HomeScreen.icons?.let {
                        it.forEach {
                            IconButton(onClick = onClick) {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = "See statistics",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    } ?: kotlin.run {
                        IconButton(onClick = onClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.statistics),
                                contentDescription = "See statistics",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                Screen.SettingsScreen -> {}

                Screen.StatsScreen -> {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "See statistics",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        },
    )
}

sealed class TopBarIcons(val icons: List<Int>?) {
    object HomeScreen : TopBarIcons(icons = null)
}
