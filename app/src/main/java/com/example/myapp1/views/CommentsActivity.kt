package com.example.myapp1.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.myapp1.models.DataStatus
import com.example.myapp1.repository.Utils.Companion.TIME_DELAY
import com.example.myapp1.viewmodels.CommentsListViewModel
import com.example.myapp1.views.theme.MyApp1Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentsActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp1Theme {
                val commentsListViewModel = hiltViewModel<CommentsListViewModel>()
                val listState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()
                var showProgress by remember { mutableStateOf(true) }
                val mContext = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        stickyHeader {
                            val text = intent.getStringExtra("name") ?: "Guest"
                            Text(
                                text = "Welcome $text,",
                            )
                            Button(modifier = Modifier.fillMaxSize(),
                                onClick = {
                                    mContext.startActivity(
                                        Intent(
                                            mContext,
                                            NextActivity::class.java
                                        )
                                    )
                                }) {
                                Text(text = "Navigate to Next Activity")
                            }
                        }
                        item {
                            ShowProgress(showProgress, true)
                        }
                        lifecycleScope.launch {
                            commentsListViewModel.getComments()
                            commentsListViewModel.commentList.observe(
                                this@CommentsActivity,
                            ) { commentList ->
                                when (commentList.status) {
                                    DataStatus.Status.LOADING -> {}
                                    DataStatus.Status.SUCCESS -> {
                                        showProgress = false
                                        commentList.data!!.forEachIndexed { index, comment ->
                                            val backgroundColor =
                                                if (index % 2 == 0) Color.LightGray else Color.White
                                            item {
                                                Column(
                                                    modifier = Modifier
                                                        .background(backgroundColor)
                                                        .fillMaxSize()
                                                        .clickable {
                                                            mContext.startActivity(
                                                                Intent(
                                                                    mContext,
                                                                    NextActivity::class.java
                                                                ).putExtra(
                                                                    "POST_ID",
                                                                    comment.postId
                                                                )
                                                            )
                                                        }
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
                                        item {
                                            Button(onClick = {
                                                coroutineScope.launch {
                                                    listState.scrollToItem(0)
                                                }
                                            }) {
                                                Text(text = "Scroll to Top")
                                            }
                                        }
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
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Composable
fun ShowProgress(loading: Boolean, isCircleType: Boolean) {
    if (!loading) return
    Column(
        modifier =
        Modifier
            .padding(48.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isCircleType) {
            CircularProgressIndicator(
                modifier = Modifier.width(45.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            LinearProgressIndicator(
                modifier =
                Modifier
                    .fillMaxSize()
                    .height(18.dp)
                    .background(Color.LightGray),
                color = Color.Green,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp1Theme {
        Greeting("Android")
    }
}

sealed class Exercise {
    class Run(val numLaps: Int) : Exercise()
    class Yoga(val numMinutes: Int) : Exercise()
}

fun main() {
    val exercise = Exercise.Yoga(30)
    println(needMat(exercise))
}

fun needMat(exercise: Exercise): Boolean {
    return when (exercise) {
        is Exercise.Run -> false
        is Exercise.Yoga -> true
    }
}


fun main1() {
    println("Hello, world!!!")
    val testString = "Welcome@2020 " + "hello 123"
    println(testString + TIME_DELAY)
}