package dev.hnatiuk.android.samples.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.hnatiuk.android.samples.compose.R

@Composable
fun ImagesBordersExampleL5Screen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        Column {
            val boxModifiers = listOf(
                Modifier.background(shape = RoundedCornerShape(16.dp), color = Color.Yellow),
                Modifier
                    .padding(top = 15.dp)
                    .background(shape = CutCornerShape(16.dp), color = Color.Green),
                Modifier
                    .padding(top = 15.dp)
                    .background(
                        shape = CircleShape, //the same as RoundedCornerShape, but corner equals 50%
                        color = Color.Blue
                    ),
                Modifier
                    .padding(top = 15.dp)
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Red,
                                Color.Yellow,
                                Color.Green
                            )
                        )
                    ),
                Modifier
                    .padding(top = 15.dp)
                    .border(width = 2.dp, color = Color.Blue)
                    .background(color = Color.Magenta),
                Modifier
                    .padding(top = 15.dp)
                    .border(
                        shape = RoundedCornerShape(16.dp),
                        width = 2.dp,
                        color = Color.Red
                    )
            )

            boxModifiers.forEach {
                BoxExample(modifier = it)
            }
        }

        Spacer(modifier = Modifier.width(100.dp))
        Column {
            //use for icon, we can apply tint
            Icon(
                painter = painterResource(id = android.R.drawable.ic_lock_power_off),
                tint = Color.Blue,
                contentDescription = null
            )

            //use for images
            Image(
                modifier = Modifier.padding(top = 15.dp),
                painter = painterResource(id = R.drawable.android_wear_image),
                contentDescription = null
            )

            AsyncImage(
                modifier = Modifier.padding(top = 15.dp),
                model = "https://developer.android.com/images/android-go/next-billion-users_856.png",
                contentDescription = null
            )
        }
    }
}

@Composable
fun BoxExample(modifier: Modifier) {
    Box(
        modifier = modifier
            .width(140.dp)
            .height(100.dp)
    )
}

@Preview
@Composable
fun ImagesBordersExampleL5Preview() {
    ImagesBordersExampleL5Screen()
}