package com.example.myapp1.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp1.models.Person
import com.example.myapp1.views.ui.theme.MyApp1Theme

class NextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyApp1Theme {
        Greeting2("Android")
    }
}

fun main() {
    val person = Person("John", 31)
    val deciderAge = 5
    println(person.copy(age = 40))
    println("John is now ${person.age} years old")
    val listOfPersons = mutableListOf<Person>()
    for (i in 1..10) {
        listOfPersons.add(Person("Name $i", i))
    }
    listOfPersons.map {
        it.group = if (it.age > deciderAge) {
            "Young"
        } else {
            "Child"
        }
    }
    println("List $listOfPersons".also { println("Size Before Filtering->${listOfPersons.size}") })
    println("Find ${listOfPersons.find { it.age > 4 }}")
    println(
        "Filter ${
            listOfPersons.filter { it.age > 4 }.apply { println("Size->${this.size}") }
        }"
    )
    println("All are above 4 ? :  ${listOfPersons.all { it.age > 4 }}")
    println("Anyone older than 4? :  ${listOfPersons.any { it.age > 4 }}")
    listOfPersons.forEach { println("Name is -> ${it.name}") }
    println("Reduce Sample - Total Age value -> " +
            "${listOfPersons.reduce { acc, persons -> acc.copy(age = acc.age + persons.age) }.age}")
}