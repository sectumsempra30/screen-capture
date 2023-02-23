package dev.hnatiuk.android.samples.course

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(userName: String) {
    Text(
        text = "User: $userName",
        fontSize = 20.sp,
        color = Color.Cyan
    )
}

@Preview(
    name = "Custom Preview",
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(userName = "Maxim Hnatiuk")
}