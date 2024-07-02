package com.example.myapp1.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.myapp1.models.DataStatus.Status.*
import com.example.myapp1.viewmodels.ListDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MainScreen(
    listState: LazyListState,
    showProgress: Boolean,
    coroutineScope: CoroutineScope,
    listViewModel: ListDataViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    mainActivity: MainActivity
): @Composable ()-> Unit {
    var showProgress1 = showProgress
    return {
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
                ShowProgress(showProgress1, false)
            }
            lifecycleScope.launch {
                listViewModel.getFlowResult()
                listViewModel.flowList.observe(
                    mainActivity,
                ) { commentList ->
                    when (commentList.status) {
                        LOADING -> {}
                        SUCCESS -> {
                            showProgress1 = false
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

                        ERROR -> {}
                    }
                }
            }
        }
    }
}