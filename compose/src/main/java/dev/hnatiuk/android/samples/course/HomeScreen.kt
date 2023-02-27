package dev.hnatiuk.android.samples.course

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(userName: String) {
    Box {
        Text(
            text = "User: $userName",
            fontSize = 20.sp,
            color = Color.Cyan,
            textAlign = TextAlign.End,
            modifier = Modifier
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .width(200.dp)
                .height(100.dp)
        )
    }
}

@Preview(
    widthDp = 400,
    heightDp = 800,
    showBackground = true,
    showSystemUi = false,
    uiMode = UI_MODE_TYPE_NORMAL
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(userName = "Maxim Hnatiuk")
}