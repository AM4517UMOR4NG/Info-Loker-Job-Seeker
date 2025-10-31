package com.example.infoloker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infoloker.data.Job
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext

@Composable
fun JobDetailScreen(job: Job, onBack: () -> Unit) {
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            Text(text = job.title, style = MaterialTheme.typography.h6)
            Text(text = job.companyName, style = MaterialTheme.typography.subtitle2)
            Text(text = job.location ?: "", style = MaterialTheme.typography.caption)

            Text(modifier = Modifier.padding(top = 12.dp), text = job.description ?: "No description")

            Button(onClick = {
                job.url?.let { url ->
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(i)
                }
            }, modifier = Modifier.padding(top = 16.dp)) {
                Text("Open Apply URL")
            }

            Button(onClick = onBack, modifier = Modifier.padding(top = 8.dp)) {
                Text("Back")
            }
        }
    }
}
