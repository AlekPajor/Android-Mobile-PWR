package com.example.dynamiclazylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dynamiclazylist.ui.theme.DynamicLazyListTheme

data class Song(
    val id: Int,
    var title: String
)

class MainActivity : ComponentActivity() {

    private val myViewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val favorite by myViewModel.favourite.collectAsState()
            var isDisplayed by remember { mutableStateOf(true) }
            var textValue by remember { mutableStateOf("") }
            var alertText by remember { mutableStateOf("") }
            var alertDisplayed by remember { mutableStateOf(false) }
            var selectedId by remember { mutableIntStateOf(0) }
            
            DynamicLazyListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text(
                            text = "My favourite songs:",
                            fontSize = 32.sp
                        )
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = textValue,
                                onValueChange = {
                                    textValue = it
                                }
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Button(
                                onClick = {
                                    isDisplayed = false
                                    val prevId = favorite.last().id
                                    myViewModel.addSong(
                                        Song(
                                            id = prevId + 1,
                                            title = textValue
                                        )
                                    )
                                    textValue = ""
                                    isDisplayed = true
                                }
                            ) {
                                Text(text = "Add")
                            }
                        }
                        Card(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxSize()
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(12.dp)
                            ) {
                                if(isDisplayed) {
                                    items(favorite) {song ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = song.title,
                                                fontSize = 30.sp
                                            )
                                            Row {
                                                Icon(
                                                    modifier = Modifier
                                                        .scale(1.4f)
                                                        .clickable {
                                                            isDisplayed = false
                                                            myViewModel.deleteSong(song)
                                                            isDisplayed = true
                                                        },
                                                    imageVector = Icons.Default.Clear,
                                                    contentDescription = null
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Icon(
                                                    modifier = Modifier
                                                        .scale(1.4f)
                                                        .clickable {
                                                            selectedId = song.id
                                                            alertDisplayed = true
                                                        },
                                                    imageVector = Icons.Default.Create,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (alertDisplayed) {
                AlertDialog(
                    onDismissRequest = {
                        alertDisplayed = false
                    },
                    text = {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "Change song's name",
                                fontSize = 22.sp
                            )
                            OutlinedTextField(
                                value = alertText,
                                onValueChange = {
                                    alertText = it
                                }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                alertDisplayed = false
                                myViewModel.updateSong(selectedId, alertText)
                                isDisplayed = false
                                selectedId = 0
                                alertText = ""
                                isDisplayed = true
                            }
                        ) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                alertDisplayed = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}