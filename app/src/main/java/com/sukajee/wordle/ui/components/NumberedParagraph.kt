package com.sukajee.wordle.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sukajee.wordle.ui.theme.VazirmatnFontFamily

@Composable
fun NumberedParagraph(
    modifier: Modifier = Modifier,
    number: Int = 0,
    text: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = VazirmatnFontFamily
            ),
            text = "$number."
        )
        Text(
            modifier = Modifier.weight(10f),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = VazirmatnFontFamily
            ),
            textAlign = TextAlign.Justify,
            text = text
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberedParagraphPreview() {
    NumberedParagraph(
        number = 10,
        text = "This is  the sample paragraph. This can go to multiple line and longer paragraph. This can even go longer and longer and longer. I have not seen such long paragraph in the past. Wow. it is super long paragraph. \n\nThis even support multiple paragraph like this. Compose is such an amazing thing. "
    )
}
