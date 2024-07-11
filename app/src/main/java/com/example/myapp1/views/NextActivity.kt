package com.example.myapp1.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.myapp1.models.Person
import com.example.myapp1.viewmodels.CommentsListViewModel
import com.example.myapp1.views.ui.theme.MyApp1Theme
import kotlinx.coroutines.launch

class NextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp1Theme {
                val commentsListViewModel = hiltViewModel<CommentsListViewModel>()
                var showProgress by remember { mutableStateOf(true) }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        lifecycleScope.launch {
                            commentsListViewModel.getCommentsForId(intent.getIntExtra("POST_ID", 0))
                            commentsListViewModel.commentList.observe(
                                this@NextActivity,
                            ) {

                            }
                        }
                    }
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
    println(
        "Reduce Sample - Total Age value -> " +
                "${listOfPersons.reduce { acc, persons -> acc.copy(age = acc.age + persons.age) }.age}"
    )
}