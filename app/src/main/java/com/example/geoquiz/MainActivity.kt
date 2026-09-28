package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GeoQuizScreen()
            }
        }
    }
}

@Composable
fun GeoQuizScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Canberra is the capital of Australia.",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { }) { Text("True") }
            Button(onClick = { }) { Text("False") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { }) { Text("Next") }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Correct answers: 0 / 6", fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GeoQuizScreenPreview() {
    MaterialTheme {
        GeoQuizScreen()
    }
}