package com.example.androidstudy.lazyLists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction


@Composable
fun LazyListsScreen(
    modifier: Modifier = Modifier,
    viewModel: LazyListsViewModel = viewModel()
) {
    val items by viewModel.items.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var inputTitle by remember { mutableStateOf("") }
    var inputContent by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Text("세로 리스트", style = MaterialTheme.typography.titleLarge)
            LazyColumnList(
                items,
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("가로 리스트", style = MaterialTheme.typography.titleLarge)
            LazyRowList(
                items,
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
            )
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "추가"
            )
        }
        if (showDialog) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusRequesterTitle = remember { FocusRequester() }
            val focusRequesterContent = remember { FocusRequester() }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.addItem(inputTitle, inputContent)
                        inputTitle = ""
                        inputContent = ""
                        showDialog = false
                        keyboardController?.hide()
                    }) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("취소")
                    }
                },
                title = { Text("새 항목 추가") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = inputTitle,
                            onValueChange = {
                                inputTitle = it.replace(Regex("[\\n\\r\\t\\u2028]"), "")
                            },
                            label = { Text("제목") },
                            modifier = Modifier.focusRequester(focusRequesterTitle),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusRequesterContent.requestFocus()
                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = inputContent,
                            onValueChange = { inputContent = it },
                            label = { Text("내용") },
                            modifier = Modifier.focusRequester(focusRequesterContent),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.addItem(inputTitle, inputContent)
                                    inputTitle = ""
                                    inputContent = ""
                                    showDialog = false
                                    keyboardController?.hide()
                                }
                            )
                        )
                    }
                }
            )

        }
    }
}


@Composable
fun LazyColumnList(items: List<CardItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
    ) {
        items(items) { item ->
            ItemCard(
                item = item,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LazyRowList(items: List<CardItem>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        items(items) { item ->
            ItemCard(item = item, modifier = Modifier.width(160.dp))
        }
    }
}

@Composable
fun ItemCard(
    item: CardItem,
    modifier: Modifier = Modifier,
    viewModel: LazyListsViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf(item.title) }
    var editContent by remember { mutableStateOf(item.content) }
    var isEditMode by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { showDialog = true }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                isEditMode = false
            },
            confirmButton = {
                if (isEditMode) {
                    TextButton(onClick = {
                        viewModel.updateItem(item.id, editTitle, editContent)
                        showDialog = false
                        isEditMode = false
                    }) {
                        Text("확인")
                    }
                } else {
                    TextButton(onClick = {
                        isEditMode = true
                    }) {
                        Text("수정")
                    }
                    TextButton(onClick = {
                        viewModel.deleteItem(item.id)
                        showDialog = false
                    }) {
                        Text("삭제", color = Color.Red)
                    }
                }
            },
            title = { Text("항목 보기") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editTitle,
                        onValueChange = {
                            editTitle = it.replace("\n", "")
                        },
                        label = { Text("제목") },
                        enabled = isEditMode,
                        modifier = if (isEditMode) Modifier.focusRequester(focusRequester) else Modifier
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editContent,
                        onValueChange = { editContent = it },
                        label = { Text("내용") },
                        enabled = isEditMode
                    )
                }
            }
        )
        if (isEditMode) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}