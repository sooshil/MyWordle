package com.sukajee.wordle.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Small Font",
    showSystemUi = true,
    showBackground = true,
    group = "Small Font Group",
    fontScale = 1.5f,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Small Font",
    showSystemUi = true,
    showBackground = true,
    group = "Small Font Group",
    fontScale = 1.5f,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Normal Font",
    showSystemUi = true,
    showBackground = true,
    group = "Normal Font Group",
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Normal Font",
    showSystemUi = true,
    showBackground = true,
    group = "Normal Font Group",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Large Font",
    showSystemUi = true,
    showBackground = true,
    group = "Large Font Group",
    fontScale = 2f,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Large Font",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
    group = "Large Font Group",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Small Font",
    showSystemUi = true,
    showBackground = true,
    group = "Small Font Group",
    fontScale = 1.5f,
    uiMode = UI_MODE_NIGHT_NO,

)
@Preview(
    name = "Small Font",
    showSystemUi = true,
    showBackground = true,
    group = "Small Font Group",
    fontScale = 1.5f,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Normal Font",
    showSystemUi = true,
    showBackground = true,
    group = "Normal Font Group",
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Normal Font",
    showSystemUi = true,
    showBackground = true,
    group = "Normal Font Group",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Large Font",
    showSystemUi = true,
    showBackground = true,
    group = "Large Font Group",
    fontScale = 2f,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Large Font",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
    group = "Large Font Group",
    uiMode = UI_MODE_NIGHT_YES
)
annotation class FontScalePreviews

@Preview(name = "Pixel XL", group = "Devices", device = Devices.PIXEL_XL, showSystemUi = true, showBackground = true)
@Preview(name = "Pixel C", group = "Devices", device = Devices.PIXEL_C, showSystemUi = true, showBackground = true)
annotation class OrientationPreviews
