package com.example.infoloker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(24.dp))

            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Avatar", tint = Color.DarkGray, modifier = Modifier.size(96.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Nama Pengguna", style = MaterialTheme.typography.h6)
            Text(text = "email@contoh.com", style = MaterialTheme.typography.body2)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}
