package com.sukajee.wordle.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HelpScreenScreenShot(
    imageId: Int,
    contentDesc: String
) {
    Image(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                RoundedCornerShape(10.dp)
            )
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(10.dp))
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp)),
        painter = painterResource(id = imageId),
        contentDescription = contentDesc,
        contentScale = ContentScale.FillWidth
    )
}
