package com.example.emojigenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emojigenerator.ui.theme.EmojiGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmojiGeneratorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmojiGeneratorApp()
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun EmojiGeneratorApp() {
    EmojiWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun EmojiWithButtonAndImage(modifier: Modifier = Modifier) {
    var number by remember { mutableStateOf(1) }
    val imageResource = when(number) {
        1 -> R.drawable.pink_1
        2 -> R.drawable.pink_2
        3 -> R.drawable.pink_3
        4 -> R.drawable.pink_4
        5 -> R.drawable.pink_5
        6 -> R.drawable.pink_6
        7 -> R.drawable.pink_7
        8 -> R.drawable.pink_8
        9 -> R.drawable.pink_9
        else -> R.drawable.pink_10
    }
    Box {
        Image(
            painter = painterResource(R.drawable.background5),
            contentDescription = "pink hearts background",
            contentScale = ContentScale.Crop,
            alpha = 0.7F
        )
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your pink emoji for today!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(imageResource),
                contentDescription = "pink emoji number $number"
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { number = (1..10).random()}) {
                Text(stringResource(id = R.string.another))
            }
        }
    }

}