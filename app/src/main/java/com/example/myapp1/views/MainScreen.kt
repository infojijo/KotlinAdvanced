package com.example.myapp1.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapp1.viewmodels.ListDataViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(listViewModel: ListDataViewModel) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            stickyHeader {
                Text(
                    text = "Hello Sticky"
                )
            }
            listViewModel?.getMyList()?.size?.let {
                items(it) {
                    Box(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .size(80.dp)
                    ) {
                        Text(text = "test")
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