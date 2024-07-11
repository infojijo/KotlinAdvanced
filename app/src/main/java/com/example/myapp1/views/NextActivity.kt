package com.example.myapp1.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.myapp1.models.DataStatus
import com.example.myapp1.models.Person
import com.example.myapp1.viewmodels.CommentsListViewModel
import com.example.myapp1.views.ui.theme.MyApp1Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NextActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp1Theme {
                val commentsListViewModel = hiltViewModel<CommentsListViewModel>()
                var showProgress by remember { mutableStateOf(true) }
                val listState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        state = listState
                    ) {
                        stickyHeader {
                            val text = intent.getIntExtra("POST_ID", 0)
                            Text(
                                text = "Comments from  $text,",
                            )
                        }
                        item {
                            ShowProgress(showProgress, false)
                        }
                        lifecycleScope.launch {
                            commentsListViewModel.getCommentsForId(intent.getIntExtra("POST_ID", 0))
                            commentsListViewModel.commentList.observe(
                                this@NextActivity,
                            ) { commentList ->
                                when (commentList.status) {
                                    DataStatus.Status.LOADING -> {}
                                    DataStatus.Status.SUCCESS -> {
                                        showProgress = false
                                        item {
                                            Box(
                                                modifier =
                                                Modifier
                                                    .padding(10.dp),
                                            ) {
                                                Text(
                                                    text = "There are - ${commentList.data!!.size} comments..",
                                                    fontSize = 16.sp,
                                                    color = Color.Black.copy(alpha = 0.5f),
                                                )
                                            }
                                        }
                                        commentList.data!!.forEachIndexed { index, comment ->
                                            val backgroundColor =
                                                if (index % 2 == 0) Color.LightGray else Color.White
                                            item {
                                                Column(
                                                    modifier = Modifier
                                                        .background(backgroundColor)
                                                        .fillMaxSize()
                                                ) {
                                                    Box(
                                                        modifier =
                                                        Modifier
                                                            .padding(10.dp),
                                                    ) {
                                                        Text(
                                                            text = """# ${comment.id} - ${comment.name}""",
                                                            fontSize = 16.sp,
                                                            color = Color.Black.copy(alpha = 0.5f),
                                                        )
                                                    }
                                                    Box(
                                                        modifier =
                                                        Modifier
                                                            .padding(10.dp),
                                                    ) {
                                                        Text(
                                                            text = comment.email,
                                                            fontSize = 15.sp,
                                                            color = Color.Black.copy(alpha = 0.5f),
                                                        )
                                                    }
                                                    Box(
                                                        modifier =
                                                        Modifier
                                                            .padding(10.dp),
                                                    ) {
                                                        Text(
                                                            text = comment.body,
                                                            fontSize = 14.sp,
                                                            color = Color.Black.copy(alpha = 0.5f),
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        coroutineScope.launch { listState.scrollToItem(0) }
                                    }

                                    DataStatus.Status.ERROR -> {}
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
}