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
    var showResult by remember { mutableStateOf(false) }

    // Ответы пользователя по каждому вопросу: null — не отвечено
    val userAnswers = remember {
        mutableStateListOf<Boolean?>().apply {
            repeat(questions.size) { add(null) }
        }
    }

    val question = questions[currentIndex]
    val isFirstQuestion = currentIndex == 0
    val isLastQuestion = currentIndex == questions.lastIndex

    // Счётчик пересчитывается автоматически из userAnswers
    val correctAnswers = userAnswers.indices.count { i ->
        userAnswers[i] != null && userAnswers[i] == questions[i].answer
    }

    fun answer(userAnswer: Boolean) {
        if (isAnswered) return
        isAnswered = true
        userAnswers[currentIndex] = userAnswer
        if (isLastQuestion) showResult = true
    }

    fun goBack() {
        if (isFirstQuestion) return
        currentIndex--
        // Сбрасываем ответ на предыдущий вопрос, чтобы можно было переответить
        userAnswers[currentIndex] = null
        isAnswered = false
    }

    fun goNext() {
        if (currentIndex < questions.lastIndex) {
            currentIndex++
            isAnswered = false
        }
    }

    fun restart() {
        currentIndex = 0
        isAnswered = false
        showResult = false
        for (i in userAnswers.indices) userAnswers[i] = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = question.text, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Как было: после ответа True/False скрываются
        if (!isAnswered) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { answer(true) }) { Text("True") }
                Button(onClick = { answer(false) }) { Text("False") }
            }
        } else {
            Spacer(modifier = Modifier.height(48.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Новая кнопка Back — недоступна на первом вопросе
            Button(
                onClick = { goBack() },
                enabled = !isFirstQuestion
            ) {
                Text("Back")
            }

            // Как было: Next скрывается, когда ответили на последний вопрос
            if (!(isLastQuestion && isAnswered)) {
                Button(onClick = { goNext() }) {
                    Text("Next")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Question ${currentIndex + 1} of ${questions.size}", fontSize = 16.sp)

    }

    if (showResult) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Quiz finished") },
            text = {
                Text("Correct answers: $correctAnswers of ${questions.size}")
            },
            confirmButton = {
                TextButton(onClick = { restart() }) {
                    Text("Restart")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GeoQuizScreenPreview() {
    MaterialTheme {
        GeoQuizScreen()
    }
}