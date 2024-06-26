package com.example.myapp1.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.myapp1.models.DataStatus
import com.example.myapp1.viewmodels.ListDataViewModel
import com.example.myapp1.views.theme.MyApp1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val listViewModel by viewModels<ListDataViewModel>()
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp1Theme {
                // A surface container using the 'background' color from the theme
                val listState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()
                var showProgress by remember { mutableStateOf(true) }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        stickyHeader {
                            Text(
                                text = "Hey, Comments updated below!!",
                            )
                        }
                        item {
                            ShowProgress(showProgress, false)
                        }
                        lifecycleScope.launch {
                            listViewModel.getFlowResult()
                            listViewModel.flowList.observe(
                                this@MainActivity,
                            ) { commentList ->
                                when (commentList.status) {
                                    DataStatus.Status.LOADING -> {}
                                    DataStatus.Status.SUCCESS -> {
                                        showProgress = false
                                        for (comment in commentList.data!!) {
                                            item {
                                                Box(
                                                    modifier =
                                                    Modifier
                                                        .padding(10.dp),
                                                ) {
                                                    Text(
                                                        text = comment.id.toString() + " - " + comment.email,
                                                        fontSize = 18.sp,
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
    ) {if(isCircleType){
        CircularProgressIndicator(
            modifier = Modifier.width(45.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )}
        else{
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
