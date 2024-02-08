package com.sukajee.wordle.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.HelpOutline
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.wordle.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    screenName: Screen,
    titleColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onNavigationIconClick: () -> Unit = { },
    onMenuIconClick: (MenuIcon) -> Unit
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
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            when (screenName) {
                Screen.HomeScreen -> {
                    TopBarIcons.HomeScreen.icons?.let {
                        it.forEach {
                            IconButton(onClick = { onMenuIconClick(it) }) {
                                Icon(
                                    imageVector = it.imageVector,
                                    contentDescription = it.contentDescription,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }

                Screen.StatsScreen -> {
                    TopBarIcons.StatsScreen.icons?.let {
                        it.forEach {
                            IconButton(onClick = { onMenuIconClick(it)}) {
                                Icon(
                                    imageVector = it.imageVector,
                                    contentDescription = it.contentDescription,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
                Screen.HelpScreen -> {
                    TopBarIcons.HelpScreen.icons?.let {
                        it.forEach {
                            IconButton(onClick = { onMenuIconClick(it)}) {
                                Icon(
                                    imageVector = it.imageVector,
                                    contentDescription = it.contentDescription,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
                Screen.SettingsScreen -> {}
            }
        },
    )
}

sealed class TopBarIcons(val icons: List<MenuIcon>?) {
    object HomeScreen : TopBarIcons(
        listOf(
            MenuIcon(
                imageVector = Icons.Outlined.HelpOutline,
                contentDescription = "Help",
                iconName = MenuIconNames.HELP
            ),
            MenuIcon(
                imageVector = Icons.Filled.BarChart,
                contentDescription = "Statistics",
                iconName = MenuIconNames.STATISTICS
            )
        )
    )
    object StatsScreen: TopBarIcons(
        listOf(
            MenuIcon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                iconName = MenuIconNames.HOME
            )
        )
    )
    object HelpScreen: TopBarIcons(
        listOf(
            MenuIcon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                iconName = MenuIconNames.HOME
            )
        )
    )
}

data class MenuIcon(
    val imageVector: ImageVector,
    val contentDescription: String,
    val iconName: MenuIconNames
)

enum class MenuIconNames {
    STATISTICS,
    HELP,
    HOME
}
