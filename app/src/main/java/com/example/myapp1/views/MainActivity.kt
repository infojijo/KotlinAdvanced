package com.example.myapp1.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        state = listState, verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        stickyHeader {
                            Text(
                                text = "Hello Sticky"
                            )
                        }
                        lifecycleScope.launch {
                            listViewModel.getFlowResult()
                            listViewModel.flowList.observe(
                                this@MainActivity
                            ) { commentList ->
                                when (commentList.status) {
                                    DataStatus.Status.LOADING -> {
                                        item {
                                            Text(text = "Data Loading from API - wait")
                                        }
                                    }
                                    DataStatus.Status.SUCCESS -> {
                                        for (comment in commentList.data!!) {
                                            item {
                                                Text(text = "$comment")
                                            }
                                        }
                                        coroutineScope.launch { listState.scrollToItem(0) }
                                    }
                                    DataStatus.Status.ERROR -> {
                                    }
                                }
                            }
                        }
                        item {
                            Button(onClick = { coroutineScope.launch { listState.scrollToItem(0) } }) {
                                Text(text = "Scroll to Top")
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp1Theme {
        Greeting("Android")
    }
}