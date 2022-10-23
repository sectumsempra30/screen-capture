package dev.hnatiuk.compose.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun HelloCompose(
    title: String = "Hello, Compose!",
    content: String = "Text from Jetpack compose"
) {
    Text(title)
    Text(content)
}

@Preview
@Composable
fun BoxSample() {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .size(200.dp)
            .padding(32.dp)
            .background(Color.Green)
            .padding(32.dp)
            .background(Color.Blue)
    )
}

@Preview(widthDp = 300, heightDp = 600)
@Composable
fun ButtonSample() {
    val counter = remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Text(
            text = "Counter value: ${counter.value}",
            style = MaterialTheme.typography.h4,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                counter.value++
            },
        ) {
            Text("Increment", color = Color.White)
        }
    }
}