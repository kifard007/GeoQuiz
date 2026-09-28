package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Question(val text: String, val answer: Boolean)

val questions = listOf(
    Question("Canberra is the capital of Australia.", true),
    Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
    Question("The Suez Canal connects the Red Sea and the Indian Ocean.", true),
    Question("The source of the Nile River is in Egypt.", false),
    Question("The Amazon River is the longest river in the Americas.", true),
    Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
)

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
    var currentIndex by remember { mutableStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) }
    val question = questions[currentIndex]
    val isLastQuestion = currentIndex == questions.lastIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = question.text, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        if (!isAnswered) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { isAnswered = true }) { Text("True") }
                Button(onClick = { isAnswered = true }) { Text("False") }
            }
        } else {
            Spacer(modifier = Modifier.height(48.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))


        if (!(isLastQuestion && isAnswered)) {
            Button(
                onClick = {
                    if (currentIndex < questions.lastIndex) {
                        currentIndex++
                        isAnswered = false
                    }
                }
            ) {
                Text("Next")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Question ${currentIndex + 1} of ${questions.size}", fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GeoQuizScreenPreview() {
    MaterialTheme {
        GeoQuizScreen()
    }
}