package com.example.infoloker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infoloker.data.Repository
import com.example.infoloker.data.Job
import com.example.infoloker.ui.JobDetailScreen
import com.example.infoloker.ui.JobListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
fun AppRoot() {
    var query by remember { mutableStateOf("") }
    var detailJob by remember { mutableStateOf<Job?>(null) }

    Column {
        TopAppBar(title = { Text("Info Loker") })

        if (detailJob != null) {
            JobDetailScreen(job = detailJob!!, onBack = { detailJob = null })
        } else {
            JobListScreen(
                query = query,
                onQueryChange = { query = it },
                onOpenDetail = { job -> detailJob = job }
            )
        }
    }
}
